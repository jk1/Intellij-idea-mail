package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session
import org.subethamail.smtp.auth.LoginFailedException
import github.jk1.smtpidea.server.pop3.Pop3Server

/**
 * todo this implementation is incorrect, it ignores apop timestamp
 */
object ApopCommand : Pop3Command("APOP"){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        if (session.authenticated) {
            session.writeErrorResponseLine("Already authenticated")
        } else if (arguments.size() == 2) {
            try {
                val authenticator = Pop3Server.authenticator
                authenticator?.login(arguments.head, arguments.last?.getBytes())
                session.authenticated = true
                session.username = arguments.head
                session.writeOkResponseLine("Authentication accepted")
            } catch(e: LoginFailedException) {
                session.writeErrorResponseLine("Username/password mismatch")
            }
        } else {
            session.writeErrorResponseLine("Login and hashed password expected")
        }
    }
}