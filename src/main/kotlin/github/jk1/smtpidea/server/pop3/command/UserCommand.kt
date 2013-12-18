package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session

/**
 *
 * @author Evgeny Naumenko
 */
object UserCommand : Pop3Command("USER"){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        if (session.authenticated) {
            session.writeErrorResponseLine("Already authenticated")
        } else if (arguments.size() == 1) {
            session.username = arguments.head
            session.writeOkResponseLine("waiting for password")
        } else {
            session.writeErrorResponseLine("USER command should have one argument")
        }
    }
}
