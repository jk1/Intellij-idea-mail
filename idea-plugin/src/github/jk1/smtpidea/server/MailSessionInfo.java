package github.jk1.smtpidea.server;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 *
 */
public class MailSessionInfo implements MessageHandler {

    private Date receivedDate;
    private String envelopeFrom;
    private Collection<String> envelopeRecipients = new ArrayList<String>();
    private MessageContext context;
    private MimeMessage message;

    public MailSessionInfo(MessageContext context) {
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void from(String from) {
        envelopeFrom = from;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void recipient(String recipient) {
        envelopeRecipients.add(recipient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void data(InputStream data) throws IOException {
        try {
            Session session = Session.getInstance(new Properties());
            message = new MimeMessage(session, data);
        } catch (MessagingException e) {
            throw new IOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void done() {
        MailStore.addMessage(this);
    }

    public static int getFieldCount() {
        return 3;
    }
}
