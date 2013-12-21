package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import github.jk1.smtpidea.store.OutboxFolder

/**
 * Removes all received mails from internal Mail Storage
 *
 * @author Evgeny Naumenko
 */
public class DeleteMailsAction : AnAction("Clear all", "Delete all mails from mail server", Icons.CLEAR) {

    override fun actionPerformed(p0: AnActionEvent?) = OutboxFolder.clear()
}
