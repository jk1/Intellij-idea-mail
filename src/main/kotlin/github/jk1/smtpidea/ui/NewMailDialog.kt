package github.jk1.smtpidea.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.project.Project
import javax.swing.JComponent
import javax.swing.JPanel
import com.intellij.ui.table.JBTable
import javax.swing.table.DefaultTableModel
import java.awt.BorderLayout
import javax.swing.JTextArea
import com.intellij.ui.components.JBScrollPane
import java.util.EventListener
import java.util.ArrayList
import javax.mail.Header


/**
 *
 */
public class NewMailDialog(project: Project?) : DialogWrapper(project) {

    private val headers = JBTable(HeaderTableModel())
    private val body =  JTextArea();

    {
        setTitle("Create new message")
        init()
    }

    override fun createCenterPanel(): JComponent {
        val contentPane =  JPanel(BorderLayout())
        val headersPane = JPanel()
        val bodyPane = JPanel()
        headersPane.add(JBScrollPane(headers))
        bodyPane.add(JBScrollPane(body))
        contentPane.add(headersPane, BorderLayout.NORTH)
        contentPane.add(bodyPane, BorderLayout.SOUTH)
        return contentPane
    }

    private class HeaderTableModel : DefaultTableModel(){

        class Header(var name : Any? = "", var value: Any? = "")

        val headerList : MutableList<Header> = arrayListOf(Header(), Header(), Header())

        override fun getRowCount() = headerList.size

        override fun getColumnCount() = 2

        override fun getColumnName(column: Int) = if (column == 1) "Header name" else "value"

        override fun getValueAt(row: Int, column: Int) = {
            if (column == 1) headerList[row].name else headerList[row].value
        }

        override fun setValueAt(aValue: Any?, row: Int, column: Int) {
            if (column == 1) headerList[row].name = aValue else headerList[row].value = aValue
            if (row == headerList.size - 1){
                headerList.add(Header())
                fireTableDataChanged()
            }
        }

        fun getHeaders() : List<Header> = headerList.filter { header -> header.name != ""}
    }

}