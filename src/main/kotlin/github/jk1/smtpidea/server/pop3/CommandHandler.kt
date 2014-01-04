package github.jk1.smtpidea.server.pop3

import java.util.HashMap
import java.util.Locale
import github.jk1.smtpidea.config.Pop3Config
import github.jk1.smtpidea.store.InboxFolder
import org.subethamail.smtp.auth.LoginFailedException
import javax.mail.internet.MimeMessage
import javax.mail.Flags.Flag
import java.util.Collections
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 *
 */
class CommandHandler(val config: Pop3Config) {

    /**
     * Parse client line, choose an appropriate POP3 command and delegate actual processing
     */
    public fun handle(commandLine: String, session: Pop3Session) {
        val tokens = commandLine.trim().split(" ").filter { !it.trim().isEmpty() }
        if (tokens.isEmpty()) {
            session.writeErrorResponseLine("Error: bad syntax")
        }
        val key = tokens.head?.toUpperCase(Locale.ENGLISH)
        when (key) {
            "CAPA" -> ::capa
            "APOP" -> withTwoArguments(::apop)
            "USER" -> withArgument(::user)
            "PASS" -> withArgument(::pass)
            "NOOP" -> ::noop
            "TOP" -> authenticated(withMessageIdArgument(withSecondIntArgument(::top)))
            "STAT" -> authenticated(::stat)
            "LIST" -> authenticated(::list)
            "RETR" -> authenticated(withMessageIdArgument(::retr))
            "DELE" -> authenticated(withMessageIdArgument(::dele))
            "RSET" -> ::rset
            "QUIT" -> ::quit
            else -> {(list: List<String>, session: Pop3Session) -> session.writeErrorResponseLine("Unknown command '$key'") }
        }(tokens.tail, session)
    }
}

// ===== WRAPPERS =======

private fun authenticated(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit {
    return {(arguments, session) ->
        if (!session.config.authEnabled || session.authenticated) {
            delegate.invoke(arguments, session)
        } else {
            session.writeErrorResponseLine("Authentication required")
        }
    }
}

private fun withArgument(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit
        = withNArguments(1, delegate)

private fun withTwoArguments(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit
        = withNArguments(2, delegate)

private fun withNArguments(expectedArgs: Int, delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit {
    return {(arguments: List<String>, session: Pop3Session) ->
        if (arguments.size() >= expectedArgs) {
            delegate.invoke(arguments, session)
        } else {
            session.writeErrorResponseLine("Command requires $expectedArgs argument(s)")
        }
    }
}

private fun withIntArgument(position: Int = 0, delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit {
    return withArgument {(arguments: List<String>, session: Pop3Session) ->
        try {
            Integer.parseInt(arguments[position]) // String#split can never return nulls in a list
            delegate.invoke(arguments, session)
        } catch(e: NumberFormatException) {
            session.writeErrorResponseLine("Message ID argument required")
        }
    }
}

private fun withSecondIntArgument(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit
        = withIntArgument(1, delegate)

private fun withMessageIdArgument(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit {
    return withIntArgument {(arguments: List<String>, session: Pop3Session) ->
        if (Integer.parseInt(arguments.head!!) <= InboxFolder.messageCount()) {
            delegate.invoke(arguments, session)
        } else {
            session.writeErrorResponseLine("Unknown message number")
        }
    }
}

// ===== PROTOCOL COMMANDS =======

private fun capa(arguments: List<String>, session: Pop3Session) {
    if (arguments.isEmpty()) {
        session.writeResponseLine("""+OK Capability list follows
TOP
USER
SASL LOGIN PLAIN
RESP-CODES
EXPIRE 60
UIDL
.""")
    } else {
        // todo
    }
}

/**
 * POP3 USER command handler, saves username for authentication
 *
 * Syntax: USER name
 */
private fun user(arguments: List<String>, session: Pop3Session) {
    session.username = arguments.head
    session.writeOkResponseLine("Waiting for password")
}

/**
 * POP3 PASS command handler, authenticates user based on login
 * passed earlier with USER command
 *
 * Syntax: PASS password
 */
private fun pass(arguments: List<String>, session: Pop3Session) {
    try {
        session.authenticator.login(session.username, arguments.head)
        session.authenticated = true
        session.writeOkResponseLine("Authentication accepted")
    } catch(e: LoginFailedException) {
        session.writeErrorResponseLine("Username/password mismatch")
    }
}

/**
 * POP3 APOP command handler, a bit more sophisticated auth facility.
 * Password is hashed with a session salt, so at least it's not plaintext
 *
 * Syntax: PASS login md5(sessionId | password)
 */
private fun apop(arguments: List<String>, session: Pop3Session) {
    try {
        session.authenticator.login(arguments.head, session.sessionId, arguments.last?.getBytes())
        session.authenticated = true
        session.username = arguments.head
        session.writeOkResponseLine("Authentication accepted")
    } catch(e: LoginFailedException) {
        session.writeErrorResponseLine("Username/password mismatch")
    }
}

/**
 * POP3 NOOP command handler. This command requires no arguments and does nothing.
 *
 * Syntax: NOOP
 */
private fun noop(arguments: List<String>, session: Pop3Session) {
    session.writeOkResponseLine()
}

/**
 * POP3 STAT command handler. STAT returns a total number of messages
 * available and total mailbox size.
 *
 * Syntax: STAT
 */
private fun stat(arguments: List<String>, session: Pop3Session) {
    session.writeOkResponseLine("${InboxFolder.messageCount()} ${InboxFolder.totalSize()}")
}

/**
 * POP3 LIST command handler. If message id is given as a parameter,
 * then message size is returned. With no params given this command
 * will print id-size paris for all messages in a mailbox.
 *
 * Syntax: LIST [id]
 */
private fun list(arguments: List<String>, session: Pop3Session) {
    when (arguments) {
        Collections.EMPTY_LIST -> listAll(session)
        else -> withMessageIdArgument(::listById)(arguments, session)
    }
}

private fun listAll(session: Pop3Session) {
    session.writeOkResponseLine("Message list follows")
    InboxFolder.getMessages().forEachWithIndex {
        (index: Int, message: MimeMessage) ->
        session.writeResponseLine("$index ${message.getSize()}")
    }
    session.writeResponseLine(".")
}

private fun listById(arguments: List<String>, session: Pop3Session) {
    val id = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
    session.writeOkResponseLine()
    session.writeResponseLine("$id ${InboxFolder[id - 1]}")
    session.writeResponseLine(".")
}

/**
 * POP3 TOP command handler. TOP returns all message headers for given
 * message id plus N lines of message body.
 *
 * Syntax: TOP id lineCount
 */
private fun top(arguments: List<String>, session: Pop3Session) {
    val id = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
    val lineCount = Integer.parseInt(arguments.tail.head!!)
    val headers = InboxFolder[id - 1].getAllHeaderLines();
    session.writeOkResponseLine("Message follows")
    while (headers!!.hasMoreElements()) {
        // todo : probably an annotation bug, investigate it
        session.writeResponseLine(headers.nextElement().toString())
    }
    if (lineCount > 0) {
        session.writeResponseLine();
        val reader: BufferedReader = BufferedReader(InputStreamReader(InboxFolder[id - 1].getRawInputStream()!!)) // todo: same shit
        for (i in 1..lineCount) {
            val line = reader.readLine()
            if (line != null) {
                session.writeResponseLine(line)
            }
        }
    }
    session.writeResponseLine("\r\n.")
    InboxFolder[id - 1].setFlag(Flag.SEEN, true)
}

/**
 * POP3 RETR command handler. Returns a message by id.
 *
 * Syntax: RETR id
 */
private fun retr(arguments: List<String>, session: Pop3Session) {
    val id = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
    session.writeOkResponseLine("Message follows")
    session.writeResponseMessage(InboxFolder[id - 1])
    session.writeResponseLine("\r\n.")
    InboxFolder[id - 1].setFlag(Flag.SEEN, true)
}

/**
 * POP3 DELE command handler. Marks message for deletion by id.
 * Actual deletion should happen on transaction commit (usually
 * it means QUIT call)
 *
 * Syntax: DELE id
 */
private fun dele(arguments: List<String>, session: Pop3Session) {
    val id = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
    InboxFolder[id - 1].setFlag(Flag.DELETED, true)
    session.writeOkResponseLine("Message $id marked for deletion")
}

/**
 * POP3 RSET command handler. Resets session state to default, voiding
 * authentication.
 *
 * Syntax: RETR
 */
private fun rset(arguments: List<String>, session: Pop3Session) {
    session.reset();
    session.writeOkResponseLine("Session is reset")
}

/**
 * POP3 QUIT command handler. Commits POPe transaction, if any
 * and closes current session
 *
 * Syntax: RETR
 */
private fun quit(arguments: List<String>, session: Pop3Session) {
    session.writeOkResponseLine("Bye")
    session.quit()
}
