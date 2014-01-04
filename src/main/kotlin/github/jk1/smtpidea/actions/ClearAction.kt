package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import github.jk1.smtpidea.store.OutboxFolder
import github.jk1.smtpidea.store.Clearable

/**
 * Removes all received mails from internal storage
 */
class ClearAction(val target: Clearable, description: String) : AnAction("Clear all", description, Icons.CLEAR) {

    override fun actionPerformed(p0: AnActionEvent?) = target.clear()

}
