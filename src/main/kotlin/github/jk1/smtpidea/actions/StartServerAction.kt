package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import github.jk1.smtpidea.server.smtp.SmtpServerManager
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import github.jk1.smtpidea.server.ServerManager
import github.jk1.smtpidea.server.pop3.Pop3ServerManager

/**
 * Launches an SMTP(S) server. This action is only enabled if server is not
 * running, so it's impossible to run two servers simultaneously.
 */
public class StartServerAction<T>(val cls: Class<T>, tooltip: String, descr: String) : AnAction(tooltip, descr, Icons.START) {

    class object {
        public val SMTP: StartServerAction<*> = StartServerAction(javaClass<SmtpServerManager>(), "Start SMTP server", "");
        public val POP3: StartServerAction<*> = StartServerAction(javaClass<Pop3ServerManager>(), "Start POP3 server", "");
    }

    override fun actionPerformed(anActionEvent: AnActionEvent?) {
        val project = anActionEvent?.getProject()
        if (project != null) {
            (ServiceManager.getService(project, cls) as ServerManager<*>).startServer() //todo: get rid of the cast
        }
    }

    override fun update(e: AnActionEvent?) {
        val project = e?.getProject()
        if (project != null) {
            val server = ServiceManager.getService(project, javaClass<SmtpServerManager>())
            if (server != null) {
                e?.getPresentation()?.setEnabled(!server.running)
            }
        }
    }
}


