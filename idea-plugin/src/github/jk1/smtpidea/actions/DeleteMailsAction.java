package github.jk1.smtpidea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import github.jk1.smtpidea.components.MailStoreComponent;

/**
 * Removes all received mails from internal Mail Storage
 *
 * @author Evgeny Naumenko
 */
public class DeleteMailsAction extends AnAction {

    public DeleteMailsAction() {
        super("Delete all mails", "Description", IconLoader.getIcon("/actions/delete.png"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            MailStoreComponent mailStore = project.getComponent(MailStoreComponent.class);
            mailStore.clear();
        }

    }
}
