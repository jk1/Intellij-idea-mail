package github.jk1.smtpidea.ui

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.table.JBTable;
import github.jk1.smtpidea.actions.ConfigureAction;
import github.jk1.smtpidea.actions.DeleteMailsAction;
import github.jk1.smtpidea.actions.StartServerAction;
import github.jk1.smtpidea.actions.StopSmtpServerAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import github.jk1.smtpidea.store.OutboxFolder
import java.awt.event.MouseListener
import com.intellij.ui.components.JBScrollPane
import com.intellij.openapi.actionSystem.AnAction
import github.jk1.smtpidea.store.InboxFolder
import java.awt.event.MouseAdapter

/**
 *
 */
public class MailToolWindowFactory : ToolWindowFactory {

    private var project: Project? = null

    public override fun createToolWindowContent(project: Project?, toolWindow: ToolWindow?) {
        this.project = project;
        val factory: ContentFactory = ContentFactory.SERVICE.getInstance()!!;
        val smtpContentPane = createSmtpContentPane();
        val smtpContent = factory.createContent(smtpContentPane, "SMTP Server", true);
        toolWindow?.getContentManager()?.addContent(smtpContent);
        val pop3ContentPane = createPop3ContentPane();
        val pop3Content = factory.createContent(pop3ContentPane, "Pop3 Server", true);
        toolWindow?.getContentManager()?.addContent(pop3Content);
    }

    private fun createSmtpContentPane(): JPanel {
        val panel = JPanel(BorderLayout());
        val mailTable = JBTable(OutboxFolder);
        mailTable.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val row = mailTable.getSelectedRow();
                if (e.getClickCount() > 1 && row != -1) { // double+ click with some row selected
                    DetailedMailViewDialog(project, OutboxFolder[row]).show();
                }
            }
        })
        mailTable.setStriped(true);
        mailTable.setExpandableItemsEnabled(true);
        mailTable.getColumnModel()?.getColumn(0)?.setPreferredWidth(100);
        mailTable.getColumnModel()?.getColumn(1)?.setPreferredWidth(100);
        panel.add(JBScrollPane(mailTable), BorderLayout.CENTER);
        panel.add(createActionsButtonPane(
                StartServerAction.SMTP,
                StopSmtpServerAction(),
                ConfigureAction(),
                DeleteMailsAction()), BorderLayout.WEST);
        return panel;
    }

    private fun createPop3ContentPane(): JPanel {
        val panel = JPanel(BorderLayout());
        val mailTable = JBTable(InboxFolder);
        mailTable.setStriped(true);
        mailTable.setExpandableItemsEnabled(true);
        panel.add(JBScrollPane(mailTable), BorderLayout.CENTER);
        panel.add(createActionsButtonPane(StartServerAction.POP3), BorderLayout.WEST);
        return panel;
    }

    private fun createActionsButtonPane(vararg actions: AnAction): JComponent {
        val group = DefaultActionGroup();
        actions.forEach { group.add(it) }
        return ActionManager.getInstance()!!
                .createActionToolbar("Actions", group, false)!!
                .getComponent()!!;
    }
}
