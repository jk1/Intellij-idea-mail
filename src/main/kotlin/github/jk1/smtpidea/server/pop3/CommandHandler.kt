package github.jk1.smtpidea.server.pop3

import java.util.HashMap
import java.util.Locale
import org.subethamail.smtp.server.InvalidCommandNameException
import org.subethamail.smtp.server.UnknownCommandException
import github.jk1.smtpidea.server.pop3.command.Pop3Command
import github.jk1.smtpidea.server.pop3.command.CapaCommand

/**
 *
 * @author Evgeny Naumenko
 */
object CommandHandler {

    private val commands: MutableMap<String, Pop3Command> = HashMap<String, Pop3Command>();

    /**
     * Adds a new command to the current handler
     */
    public fun plus (command: Pop3Command) {commands.put(command.name, command)}

    /**
     * Parse client line, choose an appropriate POP3 command and delegate actual processing
     */
    public fun handle(commandLine: String, session: Pop3Session) {
        val tokens = commandLine.trim().split(" ").filter { !it.trim().isEmpty() }
        if (tokens.isEmpty()) {
            throw InvalidCommandNameException("Error: bad syntax")
        }
        val key = tokens.head?.toUpperCase(Locale.ENGLISH)
        val command = commands.get(key)
        if (command == null) {
            throw UnknownCommandException("Error: unknown command '$key'")
        }
        command.execute(tokens.tail, session);
    }
}
