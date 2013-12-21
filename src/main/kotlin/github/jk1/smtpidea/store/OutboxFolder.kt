package github.jk1.smtpidea.store

import github.jk1.smtpidea.server.smtp.MailSession
import java.util.ArrayList
import javax.swing.SwingUtilities
import java.text.SimpleDateFormat
import java.text.DateFormat

/**
 *
 */
public object OutboxFolder : MessageFolder<MailSession>(){

    private val format: DateFormat = SimpleDateFormat("DD MMM HH:mm:ss")
    private val columns: Array<String> = array<String>("Received", "IP", "From (Envelope)", "Recipients (Envelope)")
    public val mails: MutableList<MailSession> = ArrayList<MailSession>()


    public override fun add(message: MailSession) {
        SwingUtilities.invokeLater({
            OutboxFolder.mails.add(message)
            OutboxFolder.fireTableDataChanged()
        })
    }

    public open fun clear() {
        SwingUtilities.invokeLater({
            OutboxFolder.mails.clear()
            OutboxFolder.fireTableDataChanged()
        })
    }

    override fun getMessages() = mails

    override fun messageCount() = mails.size

    public override fun get(i: Int): MailSession {
        return mails.get(i)
    }

    public override fun getColumnCount() = 4

    public override fun getRowCount() : Int = messageCount();

    public override fun getColumnName(column: Int) = columns[column]

    public override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        val info = mails.get(rowIndex)
        when (columnIndex) {
            0 -> return format.format(info.getReceivedDate())
            1 -> return info.getIp()
            2 -> return info.getEnvelopeFrom()
            3 -> return info.getEnvelopeRecipients()
        }
        throw IllegalStateException("No value defined for column $columnIndex")
    }
}