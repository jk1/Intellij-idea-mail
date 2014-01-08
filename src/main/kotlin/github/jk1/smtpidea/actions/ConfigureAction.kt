package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.ui.ConfigurationDialog
import github.jk1.smtpidea.Icons

/**
 * Launches plugin configuration dialog.
 * If configuration has been changed, then running server is restarted.
 */
object ConfigureAction : AnAction("Configure", "Open mail server configuration dialog", Icons.SETTINGS){

    override fun actionPerformed(p0: AnActionEvent?) {
        val project = p0?.getProject()
        if (project != null){
            ConfigurationDialog(project).show()
        }
    }
}