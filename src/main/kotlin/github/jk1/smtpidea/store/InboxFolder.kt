package github.jk1.smtpidea.store

import javax.mail.internet.MimeMessage
import javax.swing.SwingUtilities
import java.util.ArrayList
import javax.mail.Flags.Flag

/**
 *
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

    public override fun add(message: MimeMessage) {
        SwingUtilities.invokeAndWait({
            InboxFolder.mails.add(message)
            InboxFolder.fireTableDataChanged()
        })
    }

    public override fun clear() {
        SwingUtilities.invokeAndWait({
            InboxFolder.mails.clear()
            InboxFolder.fireTableDataChanged()
        })
    }

    /**
     * @return - sum of octet size of all messages in this folder
     */
    fun totalSize(): Int {
        return mails.fold(0, {(sum, item) -> sum + item.getSize()})
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
