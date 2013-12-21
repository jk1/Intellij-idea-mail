import javax.mail.internet.MimeMessage
import javax.swing.table.AbstractTableModel

/**
 * @author Evgeny Naumenko
 */
public abstract class MessageFolder<T> : AbstractTableModel() {

    /**
     * @return collection of all messages in this folder
     */
    public abstract fun getMessages(): List<T>

    /**
     * @return number of messages in this folder
     */
    public abstract fun messageCount(): Int

    /**
     * @return a particular message by it's number
     */
    public abstract fun get(i: Int): T


    public abstract fun add(message: T)

    public abstract fun clear()
}
