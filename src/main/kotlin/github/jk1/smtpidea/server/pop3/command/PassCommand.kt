package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session
import org.subethamail.smtp.auth.LoginFailedException
import github.jk1.smtpidea.server.pop3.Pop3Server

/**
 *
 * @author Evgeny Naumenko
 */
object PassCommand : Pop3Command("PASS"){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        if (session.authenticated) {
            session.writeErrorResponseLine("Already authenticated")
        } else if (arguments.size() == 1) {
            try {
                val authenticator = Pop3Server.authenticator
                authenticator?.login(session.username, arguments.head)
                session.authenticated = true
                session.writeOkResponseLine("Authentication accepted")
            } catch(e: LoginFailedException) {
                session.writeErrorResponseLine("Username/password mismatch")
            }
        } else {
            session.writeErrorResponseLine("PASS command should have one argument")
        }
    }
}