package github.jk1.smtpidea.actions

import github.jk1.smtpidea.server.pop3.Pop3ServerManager
import com.intellij.openapi.components.ServiceManager
import github.jk1.smtpidea.server.smtp.SmtpServerManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.actionSystem.AnAction
import github.jk1.smtpidea.store.OutboxFolder
import github.jk1.smtpidea.server.RunnableService

/**
 *  Holds all the actions plugin offers.
 *  From outside the package anyone should retrieve the actions through this registry
 */
public object ActionRegistry {

    public val START_SMTP: AnAction = object: StartAction("Start SMTP server"), WithSmtpService {}

    public val START_POP3: AnAction = object: StartAction("Start POP3 server"), WithPop3Service {}

    public val STOP_SMTP: AnAction = object: StopAction("Stop SMTP server"), WithSmtpService {}

    public val STOP_POP3: AnAction = object: StopAction("Stop POP3 server"), WithPop3Service {}

    public val CLEAR_OUTBOX_FOLDER: AnAction = ClearAction(OutboxFolder, "Delete all mails received by SMTP")

    public val CLEAR_INBOX_FOLDER: AnAction = ClearAction(OutboxFolder, "Delete all mails from POP3 Inbox")

    public val CLEAR_SMTP_LOG: AnAction = ClearAction(OutboxFolder, "Clear all log records")

    public val CLEAR_POP3_LOG: AnAction = ClearAction(OutboxFolder, "Clear all log records")

    public val CONFIGURE: AnAction = ConfigureAction()

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