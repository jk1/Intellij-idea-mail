package github.jk1.smtpidea.server.pop3

import java.net.ServerSocket
import javax.mail.internet.MimeMessage
import javax.mail.Session
import java.util.Properties
import javax.mail.internet.InternetAddress
import javax.mail.Message.RecipientType
import org.junit.Assert.*
import javax.mail.Message

public trait TestUtils{

    public fun findFreePort(): Int {
        val socket = ServerSocket(0)
        val port = socket.getLocalPort()
        socket.close()
        return port
    }

    public fun assertMailEquals(expected: Message, actual: Message) {
        assertEquals(expected.getSubject(), actual.getSubject())
        assertArrayEquals(expected.getFrom(), actual.getFrom())
        assertEquals(expected.getContent().toString().trim(), actual.getContent().toString().trim())
        assertArrayEquals(expected.getAllRecipients(), actual.getAllRecipients())
    }

    public fun createMimeMessage(content: String): MimeMessage
            = createMimeMessage("to@domain.com", "from@domain.com", content)

    public fun createMimeMessage(to: String, from: String, content: String): MimeMessage =
            createMimeMessage(to, from, "subject", content)

    public fun createMimeMessage(to: String, from: String, subject: String, content: String): MimeMessage {
        val message = MimeMessage(Session.getInstance(Properties()))
        message.setFrom(InternetAddress(from))
        message.setRecipient(RecipientType.TO, InternetAddress(to))
        message.setSubject(subject)
        message.setText(content)
        message.saveChanges()
        return message
    }
}
