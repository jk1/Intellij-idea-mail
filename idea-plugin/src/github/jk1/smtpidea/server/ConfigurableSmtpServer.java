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
public class ConfigurableSmtpServer extends SMTPServer implements MessageHandlerFactory {
    public ConfigurableSmtpServer() {
        super(new HandlerFactory());
        this.setDisableReceivedHeaders(true);
    }

    @Override
    public MessageHandler create(MessageContext ctx) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void notifyFailure(String message){
        Notification notification = new Notification("","Title", message, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }

    private static class HandlerFactory implements MessageHandlerFactory{

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageHandler create(MessageContext ctx) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
