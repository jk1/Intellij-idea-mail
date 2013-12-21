package github.jk1.smtpidea.store

import javax.mail.internet.MimeMessage
import java.util.ArrayList
import javax.mail.Flags.Flag

/**
 *
 * @author Evgeny Naumenko
 */
public object InboxFolder : MessageFolder<MimeMessage>(){

    private val columns = array("Index", "Subject", "Seen", "Deleted")

    private val mails: MutableList<MimeMessage> = ArrayList();

    override fun getMessages(): List<MimeMessage> {
        return mails;
    }

    override fun messageCount(): Int {
        return mails.size;
    }

    override fun get(i: Int): MimeMessage {
        return mails[i]
    }

    override fun add(message: MimeMessage): Unit {
        mails.add(message)
    }

    /**
     * @return - sum of octet size of all messages in this folder
     */
    fun totalSize(): Int {
        return mails.fold(0, {(sum: Int, item: MimeMessage) -> sum + item.getSize() })
    }

    override fun getColumnCount(): Int = 4

    public override fun getRowCount() : Int = messageCount();

    override fun getColumnName(column: Int): String = columns[column]

    override fun getColumnClass(columnIndex: Int) = if (columnIndex > 1) javaClass<Boolean>() else javaClass<String>()

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        when(columnIndex) {
            0 -> return rowIndex
            1 -> return this[rowIndex].getSubject();
            2 -> return this[rowIndex].isSet(Flag.SEEN)
            3 -> return this[rowIndex].isSet(Flag.DELETED)
        }
        throw IllegalArgumentException("No data available for column index $columnIndex")
    }
}
