package github.jk1.smtpidea.actions

import github.jk1.smtpidea.server.pop3.Pop3ServerManager
import com.intellij.openapi.components.ServiceManager
import github.jk1.smtpidea.server.smtp.SmtpServerManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.actionSystem.AnAction
import github.jk1.smtpidea.store.OutboxFolder
import github.jk1.smtpidea.server.RunnableService
import github.jk1.smtpidea.store.InboxFolder
import github.jk1.smtpidea.log.Pop3Log
import github.jk1.smtpidea.log.SmtpLog
import javax.swing.JTree

/**
 *  Holds all the actions plugin offers.
 *  From outside the package anyone should retrieve the actions through this registry
 */
public object ActionRegistry {

    public fun startSmtp(): AnAction = object: StartAction("Start SMTP server"), WithSmtpService {}

    public fun startPop3(): AnAction = object: StartAction("Start POP3 server"), WithPop3Service {}

    public fun stopSmtp(): AnAction = object: StopAction("Stop SMTP server"), WithSmtpService {}

    public fun stopPop3(): AnAction = object: StopAction("Stop POP3 server"), WithPop3Service {}

    public fun clearOutboxFolder(): AnAction = ClearAction(OutboxFolder, "Delete all mails received by SMTP")

    public fun clearInboxFolder(): AnAction = ClearAction(InboxFolder, "Delete all mails from POP3 Inbox")

    public fun clearSmtpLog(): AnAction = ClearAction(SmtpLog, "Clear all log records")

    public fun clearPop3Log(): AnAction = ClearAction(Pop3Log, "Clear all log records")

    public fun configure(): AnAction = ConfigureAction

    public fun expandAll(tree : JTree): AnAction = ExpandAllAction(tree)

    public fun collapseAll(tree : JTree): AnAction = CollapseAllAction(tree)

    public fun createMailAction(): AnAction = CreateMailAction

    trait WithPop3Service {
        fun getService(project: Project): RunnableService? {
            return ServiceManager.getService(project, javaClass<Pop3ServerManager>())
        }
    }

    trait WithSmtpService {
        fun getService(project: Project): RunnableService? {
            return ServiceManager.getService(project, javaClass<SmtpServerManager>())
        }
    }
}