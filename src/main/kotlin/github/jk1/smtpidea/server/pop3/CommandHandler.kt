package github.jk1.smtpidea.server.pop3

import java.util.HashMap
import java.util.Locale
import org.subethamail.smtp.server.InvalidCommandNameException
import org.subethamail.smtp.server.UnknownCommandException
import github.jk1.smtpidea.config.Pop3Config
import github.jk1.smtpidea.store.InboxFolder
import org.subethamail.smtp.auth.LoginFailedException
import javax.mail.internet.MimeMessage
import javax.mail.Flags.Flag
import java.util.Collections

/**
 *
 * @author Evgeny Naumenko
 */
object CommandHandler {

    private val commands: MutableMap<String, (List<String>, Pop3Session) -> Unit>
            = HashMap<String, (List<String>, Pop3Session) -> Unit>();

    public fun configure(config: Pop3Config) {
        commands.put("CAPA", ::capa)
        commands.put("APOP", withTwoArguments(::apop))
        commands.put("USER", withArgument(::user))
        commands.put("PASS", withArgument(::pass))
        commands.put("NOOP", ::noop)
        commands.put("TOP", authenticated(withMessageIdArgument(withSecondIntArgument(::top))))
        commands.put("STAT", authenticated(::stat))
        commands.put("LIST", authenticated(::list))
        commands.put("RETR", authenticated(withMessageIdArgument(::retr)))
        commands.put("DELE", authenticated(withMessageIdArgument(::dele)))
        commands.put("RSET", ::rset)
        commands.put("QUIT", ::quit)
    }

    /**
     * Parse client line, choose an appropriate POP3 command and delegate actual processing
     */
    public fun handle(commandLine: String, session: Pop3Session) {
        val tokens = commandLine.trim().split(" ").filter { !it.trim().isEmpty() }
        if (tokens.isEmpty()) {
            throw InvalidCommandNameException("Error: bad syntax")
        }
        val key = tokens.head?.toUpperCase(Locale.ENGLISH)
        if (commands.containsKey(key)) {
            throw UnknownCommandException("Error: unknown command '$key'")
        }
        commands.get(key)?.invoke(tokens.tail, session);
    }
}

// ===== WRAPPERS =======

private fun authenticated(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit {
    return {(arguments, session) ->
        {
            if (Pop3Server.config.authEnabled && session.authenticated) {
                delegate.invoke(arguments, session)
            } else {
                session.writeErrorResponseLine("Authentication required")
            }
        }
    }
}

private fun withArgument(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit
        = withNArguments(1, delegate)

private fun withTwoArguments(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit
        = withNArguments(2, delegate)

private fun withNArguments(expectedArgs: Int, delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit {
    return {(arguments: List<String>, session: Pop3Session) ->
        {
            if (arguments.size() >= expectedArgs) {
                delegate.invoke(arguments, session)
            } else {
                session.writeErrorResponseLine("Command requires $expectedArgs argument(s)")
            }
        }
    }
}

private fun withIntArgument(position: Int = 1, delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit {
    return withArgument {(arguments: List<String>, session: Pop3Session) ->
        {
            try {
                Integer.parseInt(arguments[position]) // String#split can never return nulls in a list
            } catch(e: NumberFormatException) {
                session.writeErrorResponseLine("Message ID argument required")
            }
        }
    }
}

private fun withSecondIntArgument(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit
        = withIntArgument(2, delegate)

private fun withMessageIdArgument(delegate: (List<String>, Pop3Session) -> Unit): (List<String>, Pop3Session) -> Unit {
    return withIntArgument {(arguments: List<String>, session: Pop3Session) ->
        {
            if (Integer.parseInt(arguments.head!!) < InboxFolder.numMessages()) {
                delegate.invoke(arguments, session)
            } else {
                session.writeErrorResponseLine("Unknown message number")
            }
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

private fun user(arguments: List<String>, session: Pop3Session) {
    session.username = arguments.head
    session.writeOkResponseLine("Waiting for password")
}

private fun pass(arguments: List<String>, session: Pop3Session) {
    try {
        val authenticator = Pop3Server.authenticator
        authenticator?.login(session.username, arguments.head)
        session.authenticated = true
        session.writeOkResponseLine("Authentication accepted")
    } catch(e: LoginFailedException) {
        session.writeErrorResponseLine("Username/password mismatch")
    }
}

private fun apop(arguments: List<String>, session: Pop3Session) {
    val authenticator = Pop3Server.authenticator
    authenticator?.login(arguments.head, session.sessionId, arguments.last?.getBytes())
    session.authenticated = true
    session.username = arguments.head
    session.writeOkResponseLine("Authentication accepted")
}

private fun noop(arguments: List<String>, session: Pop3Session) {
    session.writeOkResponseLine()
}

private fun stat(arguments: List<String>, session: Pop3Session) {
    session.writeOkResponseLine("${InboxFolder.numMessages()} ${InboxFolder.totalSize()}")
}

private fun list(arguments: List<String>, session: Pop3Session) {
    when (arguments) {
        Collections.EMPTY_LIST -> listAll(arguments, session)
        else -> withMessageIdArgument(::listById)(arguments, session)
    }
}

private fun listAll(arguments: List<String>, session: Pop3Session) {
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
    session.writeResponseLine("$id ${InboxFolder.getMessages()[id]}")
    session.writeResponseLine(".")
}

private fun top(arguments: List<String>, session: Pop3Session) {
    val id = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
    val linesCount = Integer.parseInt(arguments.tail.head!!)

    session.writeOkResponseLine("Message follows")
    session.writeResponseMessage(InboxFolder.getMessages()[id])
    session.writeResponseLine("\r\n.")
}

private fun retr(arguments: List<String>, session: Pop3Session) {
    val id = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
    session.writeOkResponseLine("Message follows")
    session.writeResponseMessage(InboxFolder.getMessages()[id])
    session.writeResponseLine("\r\n.")
}

private fun dele(arguments: List<String>, session: Pop3Session) {
    val id = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
    InboxFolder.getMessages()[id].setFlag(Flag.DELETED, true)
    session.writeOkResponseLine("Message $id marked for deletion")
}

private fun rset(arguments: List<String>, session: Pop3Session) {
    session.reset();
    session.writeOkResponseLine("Session is reset")
}

private fun quit(arguments: List<String>, session: Pop3Session) {
    session.writeOkResponseLine("Bye")
    session.quit()
}
