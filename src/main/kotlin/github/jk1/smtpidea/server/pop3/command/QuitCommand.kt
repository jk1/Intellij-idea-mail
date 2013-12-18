package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session

/**
 *
 */
object QuitCommand : Pop3Command("QUIT") {

    override fun execute(arguments: List<String>, session: Pop3Session) {
        session.writeOkResponseLine("Bye")
        session.quit()
    }
}