package github.jk1.smtpidea.server

import javax.mail.internet.MimeMessage
import github.jk1.smtpidea.store.InboxFolder
import java.io.BufferedReader
import java.io.InputStreamReader
import github.jk1.smtpidea.server.pop3.Pop3Session

/**
 * Extension functions for MimeMessage to support
 *
 * @author Evgeny Naumenko
 */
public object MimeMessageUtils{
    public fun MimeMessage.writeTo(session: Pop3Session, lineCount: Int) {
        val headers = this.getAllHeaderLines();
        while (headers!!.hasMoreElements()) {
            // todo : probably an annotation bug, investigate it
            session.writeResponseLine(headers.nextElement().toString())
        }
        val reader: BufferedReader = BufferedReader(InputStreamReader(getRawInputStream()!!)) // todo: same shit
        for (i in 1..lineCount) {
            val line = reader.readLine()
            if (line != null) {
                session.writeResponseLine(line)
            }
        }
    }
}