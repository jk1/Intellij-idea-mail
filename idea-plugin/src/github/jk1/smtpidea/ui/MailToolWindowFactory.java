package github.jk1.smtpidea.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.table.JBTable;
import github.jk1.smtpidea.actions.ConfigureAction;
import github.jk1.smtpidea.actions.DeleteMailsAction;
import github.jk1.smtpidea.actions.StartServerAction;
import github.jk1.smtpidea.actions.StopServerAction;
import github.jk1.smtpidea.components.MailStoreComponent;
import github.jk1.smtpidea.server.MailSessionInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Evgeny Naumenko
 */
public class MailToolWindowFactory implements ToolWindowFactory {

    private Project project;
    private JBTable mailTable;
    private MailStoreComponent.MailTableModel tableModel;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        this.project = project;
        ContentFactory factory = ContentFactory.SERVICE.getInstance();
        MailStoreComponent component = project.getComponent(MailStoreComponent.class);
        JPanel contentPane = this.createContentPane(component.getTableModel());
        Content content = factory.createContent(contentPane, "", true);
        toolWindow.getContentManager().addContent(content);
    }

    private JPanel createContentPane(MailStoreComponent.MailTableModel model) {
        tableModel = model;
        JPanel panel = new JPanel(new BorderLayout());
        mailTable = new JBTable(model);
        mailTable.addMouseListener(new MailTableMouseListener());
        mailTable.setDefaultRenderer(Iterable.class, new MultilineCellRenderer());
        mailTable.setStriped(true);
        mailTable.setExpandableItemsEnabled(true);
        mailTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        mailTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        JScrollPane pane = new JBScrollPane(mailTable);
        panel.add(pane, BorderLayout.CENTER);
        panel.add(createButtonPane(), BorderLayout.NORTH);
        return panel;
    }

    private JComponent createButtonPane() {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new StartServerAction());
        group.add(new StopServerAction());
        group.add(new ConfigureAction());
        group.add(new DeleteMailsAction());
        return ActionManager.getInstance().createActionToolbar("Actions", group, true).getComponent();
    }

    private class MailTableMouseListener extends MouseAdapter {

        /**
         * {@inheritDoc}
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 1) { //double+ click
                int row = mailTable.getSelectedRow();
                if (row != -1) {
                    MailSessionInfo info = tableModel.getMailSessionInfo(row);
                    new DetailedMailViewDialog(project, info).show();
                }
            }
        }
    }
}
