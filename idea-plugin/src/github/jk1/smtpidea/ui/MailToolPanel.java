package github.jk1.smtpidea.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import github.jk1.smtpidea.actions.ConfigureAction;
import github.jk1.smtpidea.actions.DeleteMailsAction;
import github.jk1.smtpidea.actions.StartServerAction;
import github.jk1.smtpidea.actions.StopServerAction;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 *
 */
public class MailToolPanel extends JPanel {

    public MailToolPanel(TableModel model) {
        super(new BorderLayout());
        JTable table = new JBTable(model);
        table.setDefaultRenderer(Iterable.class, new MultilineCellRenderer());
        JScrollPane pane = new JBScrollPane(table);
        this.add(pane, BorderLayout.CENTER);
        this.add(createButtonPane(), BorderLayout.NORTH);
    }

    private JComponent createButtonPane(){
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new StartServerAction());
        group.add(new StopServerAction());
        group.add(new ConfigureAction());
        group.add(new DeleteMailsAction());
        return ActionManager.getInstance().createActionToolbar("Actions", group, true).getComponent();
    }
}
