package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session

/**
 *
 * @author Evgeny Naumenko
 */
object NoopCommand : Pop3Command ("NOOP"){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        session.writeOkResponseLine()
    }
}
