package github.jk1.smtpidea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import github.jk1.smtpidea.server.MailSessionInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 */
public class DetailedMailViewDialog extends DialogWrapper {

    private MailSessionInfo info;
    private JBTabbedPane tabbedPane;

    protected DetailedMailViewDialog(@Nullable Project project, MailSessionInfo info) {
        super(project);
        this.setTitle("Incoming Mail View");
        this.info = info;
        init();
    }

    @NotNull
    @Override
    protected JComponent createCenterPanel() {
        tabbedPane = new JBTabbedPane();
        this.addTab("Raw", info.getRawMessage());
        this.addTab("Text", info.getFormattedMessage());
        this.addTab("Html", info.getFormattedMessage());
        return tabbedPane;
    }

    private void addTab(String title, String data) {
        JPanel panel = new JPanel();
        JTextPane pane = new JTextPane();
        pane.setText(data);
        panel.add(new JBScrollPane(pane));
        tabbedPane.addTab(title, panel);
    }
}
