package github.jk1.smtpidea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import github.jk1.smtpidea.server.MailSessionInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 */
public class DetailedMailViewDialog extends DialogWrapper {

    private MailSessionInfo info;

    protected DetailedMailViewDialog(@Nullable Project project, MailSessionInfo info) {
        super(project);
        this.info = info;
        init();
    }

    @NotNull
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        JTextPane pane = new JTextPane();
        pane.setText(info.getRawMessage());
        panel.add(pane);
        return panel;
    }
}
