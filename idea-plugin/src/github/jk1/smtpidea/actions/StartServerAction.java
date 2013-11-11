package github.jk1.smtpidea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import github.jk1.smtpidea.server.SmtpServerManager;

/**
 *
 * @author Evgeny Naumenko
 */
public class StartServerAction extends AnAction {

    public StartServerAction() {
        super("Start mail server", "Description", IconLoader.getIcon("/general/run.png"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            SmtpServerManager server = ServiceManager.getService(project, SmtpServerManager.class);
            server.startServer();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            SmtpServerManager server = ServiceManager.getService(project, SmtpServerManager.class);
            anActionEvent.getPresentation().setEnabled(!server.isRunning());
        }
    }
}
