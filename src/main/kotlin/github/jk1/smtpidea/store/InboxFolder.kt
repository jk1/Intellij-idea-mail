package github.jk1.smtpidea.store

import javax.mail.internet.MimeMessage
import java.util.ArrayList

/**
 *
 * @author Evgeny Naumenko
 */
public object InboxFolder : MessageFolder{

    private val messages: MutableList<MimeMessage> = ArrayList();

    override fun getMessages(): List<MimeMessage> {
        return messages;
    }
    override fun numMessages(): Int {
        return messages.size;
    }
    override fun totalSize(): Int {
        return messages.fold(0, {(sum: Int, item: MimeMessage) -> sum + item.getSize() })
    }
}
