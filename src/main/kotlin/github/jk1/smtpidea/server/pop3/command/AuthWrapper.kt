package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session

/**
 *
 */
class AuthWrapper(val delegate: Pop3Command) : Pop3Command(delegate.name){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        if (session.authenticated) {
            delegate.execute(arguments, session)
        } else {
            session.writeErrorResponseLine("Authentication required")
        }
    }
}
