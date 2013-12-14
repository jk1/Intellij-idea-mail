package github.jk1.smtpidea.server.smtp;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import github.jk1.smtpidea.components.MailStoreComponent;
import org.subethamail.smtp.server.SMTPServer;

/**
 *
 */
public class SmtpServerManager {

    private SMTPServer server;
    private MailStoreComponent mailStore;
    private ServerConfiguration configuration;

    public SmtpServerManager(Project project) {
        mailStore = project.getComponent(MailStoreComponent.class);
    }

    public void setConfiguration(ServerConfiguration configuration) {
        this.configuration = configuration;
        // restart server on configuration change
        if (this.isRunning()) {
            this.stopServer();
            this.startServer();
        }
    }

    public void startServer() {
        if (configuration.transportSecurity == ServerConfiguration.TransportSecurity.SSL) {
            server = new SmtpsMailServer(mailStore, configuration);
        } else {
            server = new SmtpMailServer(mailStore, configuration);
        }
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            this.notifyFailure(e.getMessage());
        }
    }

    public void stopServer() {
        try {
            if (server.isRunning()) {
                server.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.notifyFailure(e.getMessage());
        }
    }

    public boolean isRunning() {
        return server != null && server.isRunning();
    }

    private void notifyFailure(String message) {
        Notification notification = new Notification("", "Title", message, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }
}
