package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import github.jk1.smtpidea.server.smtp.SmtpServerManager
import com.intellij.openapi.components.ServiceManager

/**
 * Stops SMTP(S) server that is currently running.
 * This action is enabled if there is a server running at the moment.
 *
 * @author Evgeny Naumenko
 */
public class StopSmtpServerAction() : AnAction("Stop mail server", "Description", Icons.STOP) {

    public override fun actionPerformed(anActionEvent: AnActionEvent?): Unit {
        val project = anActionEvent?.getProject()
        if (project != null) {
            ServiceManager.getService(project, javaClass<SmtpServerManager>())?.stopServer()
        }
    }


    public override fun update(e: AnActionEvent?): Unit {
        val project = e?.getProject()
        if (project != null) {
            val server = ServiceManager.getService(project, javaClass<SmtpServerManager>())
            if (server != null) {
                e?.getPresentation()?.setEnabled(server.isRunning())
            }
        }
    }
}
