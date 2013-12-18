package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session
import github.jk1.smtpidea.store.InboxFolder

/**
 *
 */
object RetrCommand : Pop3Command("RETR", wrap = true){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        if (arguments.size() == 1) {
            try {
                val number = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
                if (number < InboxFolder.getMessages().size()) {
                    session.writeOkResponseLine("Message follows")
                    session.writeResponseMessage(InboxFolder.getMessages()[number])
                    session.writeResponseLine("\r\n.\r\n")
                } else {
                    session.writeErrorResponseLine("Unknown message number")
                }
            } catch(e: NumberFormatException) {
                session.writeErrorResponseLine("${arguments.head} is not a valid message number")
            }
        } else {
            session.writeErrorResponseLine("RETR requires message number as an argument")
        }
    }
}