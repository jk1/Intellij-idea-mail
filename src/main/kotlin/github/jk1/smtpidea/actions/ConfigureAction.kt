package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.ui.ConfigurationDialog
import github.jk1.smtpidea.Icons

/**
 * Launches plugin configuration dialog.
 * If configuration has been changed running SMTP server is restarted.
 */
public class ConfigureAction : AnAction("Configure", "Description", Icons.SETTINGS){

    override fun actionPerformed(p0: AnActionEvent?) {
        val project = p0?.getProject()
        if (project != null){
            ConfigurationDialog(project).show()
        }
    }
}