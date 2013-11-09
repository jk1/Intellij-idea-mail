package github.jk1.smtpidea.server;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.server.SMTPServer;

/**
 *
 */
public class ConfigurableSmtpServerImpl implements ConfigurableSmtpServer {

    private SMTPServer server = new SMTPServer(new HandlerFactory());
    private ServerConfiguration configuration;

    @Override
    public void setConfiguration(ServerConfiguration configuration) {
        this.configuration = configuration;
        if (server.isRunning()) {
            this.stopServer();
            this.startServer();
        }
    }

    @Override
    public void startServer() {
        try {
            server = new SMTPServer(new HandlerFactory());
            server.setDisableReceivedHeaders(true);
            server.setPort(configuration.port);
            server.setSoftwareName("Intellij Idea ESMTP Server");
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            this.notifyFailure(e.getMessage());
        }
    }

    @Override
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

    @Override
    public boolean isRunning() {
        return server.isRunning();
    }

    private void notifyFailure(String message) {
        Notification notification = new Notification("", "Title", message, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }

    private static class HandlerFactory implements MessageHandlerFactory {

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageHandler create(MessageContext ctx) {
            return new MailSessionInfo(ctx);
        }
    }
}
