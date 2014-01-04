package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import com.intellij.openapi.project.Project
import github.jk1.smtpidea.server.RunnableService

/**
 * Stops server that is currently running.
 * This action is enabled if there is a server running at the moment.
 */
abstract class StopAction(description : String) : AnAction("Stop server", description, Icons.STOP) {

    public override fun actionPerformed(anActionEvent: AnActionEvent?): Unit {
        val project = anActionEvent?.getProject()
        if (project != null) {
            getService(project)?.stop()
        }
    }

    public override fun update(e: AnActionEvent?): Unit {
        val project = e?.getProject()
        if (project != null) {
            val service = getService(project)
            if (service != null) {
                e?.getPresentation()?.setEnabled(service.running)
            }
        }
    }

    abstract fun getService(project: Project): RunnableService?
}
