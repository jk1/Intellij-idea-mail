package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session
import github.jk1.smtpidea.server.pop3.CommandHandler

/**
 *
 */
object TopCommand : Pop3Command("TOP", true){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        throw UnsupportedOperationException()
    }
}
