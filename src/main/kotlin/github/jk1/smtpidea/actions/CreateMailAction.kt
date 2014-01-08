package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons

/**
 *
 */
object CreateMailAction : AnAction("Create new message", "Create new e-mail in our imaginary POP3 mailbox", Icons.ADD){

    override fun actionPerformed(action: AnActionEvent?) {
        throw UnsupportedOperationException()
    }
}