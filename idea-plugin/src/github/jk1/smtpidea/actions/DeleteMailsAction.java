package github.jk1.smtpidea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;

/**
 *
 */
public class DeleteMailsAction extends AnAction {

    public DeleteMailsAction() {
        super("Delete all mails", "Description", IconLoader.getIcon("/actions/delete.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
