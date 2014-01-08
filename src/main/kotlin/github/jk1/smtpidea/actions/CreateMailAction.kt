package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import github.jk1.smtpidea.ui.NewMailDialog

/**
 *  Opens dialog window to compose new e-mail to be placed on our toy server
 */
object CreateMailAction : AnAction("Create new message", "Create new e-mail in our imaginary POP3 mailbox", Icons.ADD){

    override fun actionPerformed(p0: AnActionEvent?) {
        val project = p0?.getProject()
        if (project != null){
            NewMailDialog(project).show()
        }
    }
}