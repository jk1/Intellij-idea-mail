package github.jk1.smtpidea.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTabbedPane
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextPane
import com.intellij.ui.components.JBScrollPane
import github.jk1.smtpidea.server.smtp.MailSession

/**
 * Dialog showing detailed message contents: headers, body and so on
 *
 * @author Evgeny Naumenko
 */
public class DetailedMailViewDialog(project: Project?, val info: MailSession) : DialogWrapper(project){

    private val tabbedPane = JBTabbedPane();

    {
        setTitle("Incoming Mail View")
        init()
    }

    override fun createCenterPanel(): JComponent {
        this.addTab("Raw", info.getRawMessage())
        this.addTab("Text", info.getFormattedMessage())
        return tabbedPane
    }

    private fun addTab(title: String, data: String) {
        val panel = JPanel()
        val pane = JTextPane()
        pane.setText(data)
        panel.add(JBScrollPane(pane))
        tabbedPane.addTab(title, panel)
    }
}
