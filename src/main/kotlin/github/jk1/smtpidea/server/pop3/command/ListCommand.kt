package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session
import github.jk1.smtpidea.store.InboxFolder
import javax.mail.internet.MimeMessage

/**
 *
 */
object ListCommand : Pop3Command("LIST", wrap = true){

    override fun execute(arguments: List<String>, session: Pop3Session) {
        if (arguments.isEmpty()) {
            session.writeOkResponseLine("Message list follows")
            InboxFolder.getMessages().forEachWithIndex {
                (index: Int, message: MimeMessage) ->
                session.writeResponseLine("$index ${message.getSize()}")
            }
            session.writeResponseLine(".")
        } else {
            try {
                val number = Integer.parseInt(arguments.head!!) // String#split can never return nulls in a list
                if (number < InboxFolder.getMessages().size()) {
                    session.writeOkResponseLine()
                    session.writeResponseLine("$number ${InboxFolder.getMessages()[number]}")
                    session.writeResponseLine(".")
                } else {
                    session.writeErrorResponseLine("Unknown message number")
                }
            } catch(e: NumberFormatException) {
                session.writeErrorResponseLine("${arguments.head} is not a valid message number")
            }
        }
    }
}