package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session
import github.jk1.smtpidea.server.pop3.CommandHandler


/**
 *
 */
object CapaCommand : Pop3Command("CAPA") {

    override fun execute(arguments: List<String>, session: Pop3Session) {
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

        }
    }
}
