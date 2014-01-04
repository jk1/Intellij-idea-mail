package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import com.intellij.openapi.project.Project
import github.jk1.smtpidea.server.RunnableService

/**
 * Launches mail server. This action is only enabled if server of the same kind
 * is not running, so it's impossible to run two SMTP servers simultaneously.
 */
abstract class StartAction(description: String) : AnAction("Start server", description, Icons.START) {

    override fun actionPerformed(anActionEvent: AnActionEvent?) {
        val project = anActionEvent?.getProject()
        if (project != null) {
            getService(project)?.start()
        }
    }

    override fun update(e: AnActionEvent?) {
        val project = e?.getProject()
        if (project != null) {
            val service = getService(project)
            if (service != null) {
                e?.getPresentation()?.setEnabled(!service.running)
            }
        }
    }

    abstract fun getService(project: Project): RunnableService?
}


