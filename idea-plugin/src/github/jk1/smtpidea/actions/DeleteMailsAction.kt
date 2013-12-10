package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.components.MailStoreComponent
import github.jk1.smtpidea.Icons

/**
 * Removes all received mails from internal Mail Storage
 *
 * @author Evgeny Naumenko
 */
public class DeleteMailsAction : AnAction("Clear all", "Delete all mails from mail server", Icons.CLEAR) {

    override fun actionPerformed(event: AnActionEvent?) {
        event?.getProject()?.getComponent(javaClass<MailStoreComponent>())?.clear()
    }
}
