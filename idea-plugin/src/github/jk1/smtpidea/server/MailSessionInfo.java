package github.jk1.smtpidea.server;

import github.jk1.smtpidea.components.MailStoreComponent;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 *
 */
public class MailSessionInfo implements MessageHandler {

    private MailStoreComponent mailStore;

    private Date receivedDate;
    private String envelopeFrom;
    private Collection<String> envelopeRecipients = new ArrayList<String>();
    private MessageContext context;
    private MimeMessage message;

    public MailSessionInfo(MessageContext context, MailStoreComponent mailStore) {
        this.context = context;
        this.mailStore = mailStore;
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
        receivedDate = new Date();
        mailStore.addMessage(this);
    }


    public Date getReceivedDate(){
      return receivedDate;
    }

    public String getEnvelopeFrom() {
        return envelopeFrom;
    }

    public Collection<String> getEnvelopeRecipients() {
        return envelopeRecipients;
    }

    public String getRawMessage() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            message.writeTo(stream);
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(stream));
        } catch (MessagingException e) {
            e.printStackTrace(new PrintWriter(stream));
        }
        return new String(stream.toByteArray());
    }
}
