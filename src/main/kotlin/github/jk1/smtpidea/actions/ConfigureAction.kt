package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.ui.ConfigurationDialog
import github.jk1.smtpidea.Icons

/**
 * Launches plugin configuration dialog.
 * If configuration has been changed running SMTP server is restarted.
 *
 * @author Evgeny Naumenko
 */
public class ConfigureAction : AnAction("Configure", "Description", Icons.SETTINGS){

    override fun actionPerformed(event : AnActionEvent?) {
        val project = event?.getProject()
        if (project != null){
            ConfigurationDialog(event?.getProject()).show()
        }
    }
}