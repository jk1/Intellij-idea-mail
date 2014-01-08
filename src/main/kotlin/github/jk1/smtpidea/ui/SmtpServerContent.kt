package github.jk1.smtpidea.ui

import javax.swing.JPanel
import java.awt.BorderLayout
import com.intellij.ui.table.JBTable
import github.jk1.smtpidea.store.OutboxFolder
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import com.intellij.ui.components.JBScrollPane
import com.intellij.openapi.project.Project
import github.jk1.smtpidea.actions.ActionRegistry
import github.jk1.smtpidea.actions.ConfigureAction
import github.jk1.smtpidea.actions.ClearAction

/**
 *  SMTP server representation tab fot plugin tool window.
 *  Contains a table of available mails, submitter though SMTP
 *  and server-related actions (start/stop/configure/etc.)
 */
class SmtpServerContent(val project: Project) : BaseContent("SMTP Server") {

    {
        val mailTable = JBTable(OutboxFolder);
        mailTable.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val row = mailTable.getSelectedRow();
                if (e.getClickCount() > 1 && row != -1) {
                    // double+ click with some row selected
                    DetailedMailViewDialog(project, OutboxFolder[row]).show();
                }
            }
        })
        mailTable.setStriped(true);
        mailTable.setExpandableItemsEnabled(true);
        mailTable.getColumnModel()?.getColumn(0)?.setPreferredWidth(100);
        mailTable.getColumnModel()?.getColumn(1)?.setPreferredWidth(100);
        add(JBScrollPane(mailTable), BorderLayout.CENTER);
        add(createActionsButtonPane(
                ActionRegistry.startSmtp(),
                ActionRegistry.stopSmtp(),
                ActionRegistry.configure(),
                ActionRegistry.clearOutboxFolder()), BorderLayout.WEST);
    }
}