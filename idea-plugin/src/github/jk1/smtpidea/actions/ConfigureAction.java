package github.jk1.smtpidea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.IconLoader;
import github.jk1.smtpidea.ui.ConfigurationDialog;

/**
 * Launches plugin configuration dialog.
 * If configuration has been changed running SMTP server is restarted.
 *
 * @author Evgeny Naumenko
 */
public class ConfigureAction extends AnAction {

    public ConfigureAction() {
        super("Configure", "Description", IconLoader.getIcon("/general/settings.png"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final AnActionEvent anActionEvent) {
        final Project project = anActionEvent.getProject();
        if (project != null) {
            DialogWrapper dialog = new ConfigurationDialog(anActionEvent.getProject());
            dialog.show();
        }
    }

}
