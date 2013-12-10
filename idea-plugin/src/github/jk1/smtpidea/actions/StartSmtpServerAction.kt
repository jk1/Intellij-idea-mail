package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import github.jk1.smtpidea.server.smtp.SmtpServerManager
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons

/**
 * Launches an SMTP(S) server. This action is only enabled if server is not
 * running, so it's impossible to run two servers simultaneously.
 *
 * @author Evgeny Naumenko
 */
public class StartSmtpServerAction() : AnAction("Start mail server", "Description", Icons.START) {
    /**
     * {@inheritDoc}
     */
    override fun actionPerformed(anActionEvent: AnActionEvent?) {
        val project = anActionEvent?.getProject()
        if (project != null) {
            ServiceManager.getService(project, javaClass<SmtpServerManager>())?.startServer()
        }
    }
    /**
     * {@inheritDoc}
     */
    override fun update(anActionEvent: AnActionEvent?) {
        val project = anActionEvent?.getProject()
        if (project != null) {
            val server = ServiceManager.getService(project, javaClass<SmtpServerManager>())
            if (server != null) {
                anActionEvent?.getPresentation()?.setEnabled(!server.isRunning())
            }
        }
    }


}
