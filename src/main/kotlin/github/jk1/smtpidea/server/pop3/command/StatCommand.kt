package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session
import github.jk1.smtpidea.store.InboxFolder

/**
 *
 */
object StatCommand : Pop3Command("STAT", true){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        session.writeOkResponseLine("${InboxFolder.numMessages()} ${InboxFolder.totalSize()}")
    }
}
