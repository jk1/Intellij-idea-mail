package github.jk1.smtpidea.server.smtp

import org.subethamail.smtp.MessageHandler
import java.util.Date
import java.util.ArrayList
import org.subethamail.smtp.MessageContext
import javax.mail.internet.MimeMessage
import java.io.InputStream
import javax.mail.Session
import java.util.Properties
import github.jk1.smtpidea.store.OutboxFolder
import java.io.ByteArrayOutputStream
import javax.mail.Message
import javax.mail.Multipart

/**
 *
 */
public class MailSession(val context: MessageContext?) : MessageHandler{

    public var receivedDate: Date? = null
    public var envelopeFrom: String? = null
    public var envelopeRecipients: ArrayList<String> = ArrayList<String>()
    public var message: MimeMessage? = null
    public val ip: String = context?.getRemoteAddress().toString()

    override fun from(from: String?) {
        envelopeFrom = from
    }

    override fun recipient(recipient: String?) {
        envelopeRecipients.add(recipient as String)
    }

    override fun data(data: InputStream?) {
        message = MimeMessage(Session.getInstance(Properties()), data)
    }

    override fun done() {
        receivedDate = Date()
        OutboxFolder.add(this)
    }

    public fun getRawMessage(): String {
        val stream = ByteArrayOutputStream()
        message?.writeTo(stream)
        return String(stream.toByteArray())
    }

    public fun getFormattedMessage(): String {
        val builder = StringBuilder()
        handleMessage(message, builder)
        return builder.toString()
    }

    public fun handleMessage(message: Message?, builder: StringBuilder) {
        val content = message?.getContent()
        when (content) {
            is Multipart -> handleMultipart(content, builder)
            else -> builder.append(content)
        }
    }

    public fun handleMultipart(mp: Multipart, builder: StringBuilder) {
        for (i in 0..mp.getCount() - 1) {
            val bodyPart = mp.getBodyPart(i)
            val content = bodyPart?.getContent()
            when (content) {
                is Multipart -> handleMultipart(content, builder)
                is InputStream -> builder.append("\n").append("[Binary data]").append("\n")
                else -> builder.append(content)
            }
        }
    }
}
