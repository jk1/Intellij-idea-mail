package github.jk1.smtpidea.server

import github.jk1.smtpidea.config.ServerConfig
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

/**
 *
 */
public trait ServerManager<T : ServerConfig> {

    public var running: Boolean

    public open var configuration: T
        get() = configuration
        public set(configuration: T) {
            this.configuration = configuration
            // restart server on configuration change
            if (running) {
                stopServer()
                startServer()
            }
        }

    public fun startServer()

    public fun stopServer()

    protected fun notifyFailure(message: String): Unit {
        val notification = Notification("", "Title", message, NotificationType.ERROR)
        Notifications.Bus.notify(notification)
    }
}
