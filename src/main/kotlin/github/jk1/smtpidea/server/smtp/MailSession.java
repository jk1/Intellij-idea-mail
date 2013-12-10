package github.jk1.smtpidea.server.smtp;

import github.jk1.smtpidea.components.MailStoreComponent;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;

import javax.mail.*;
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
public class MailSession implements MessageHandler {

    private MailStoreComponent mailStore;

    private Date receivedDate;
    private String envelopeFrom;
    private Collection<String> envelopeRecipients = new ArrayList<String>();
    private MessageContext context;
    private MimeMessage message;

    public MailSession(MessageContext context, MailStoreComponent mailStore) {
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


    public Date getReceivedDate() {
        return receivedDate;
    }

    public String getEnvelopeFrom() {
        return envelopeFrom;
    }

    public Collection<String> getEnvelopeRecipients() {
        return envelopeRecipients;
    }

    public String getIp() {
        return context.getRemoteAddress().toString();
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

    public String getFormattedMessage() {
        try {
            StringBuilder builder = new StringBuilder();
            this.handleMessage(message, builder);
            return builder.toString();
        } catch (IOException e) {
            return e.getMessage();
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }

    public void handleMessage(Message message, StringBuilder builder) throws IOException, MessagingException {
        Object content = message.getContent();
        if (content instanceof String) {
            builder.append(content);
        } else if (content instanceof Multipart) {
            handleMultipart((Multipart) content, builder);
        }
    }

    public void handleMultipart(Multipart mp, StringBuilder builder) throws MessagingException, IOException {
        for (int i = 0; i < mp.getCount(); i++) {
            BodyPart bp = mp.getBodyPart(i);
            Object content = bp.getContent();
            if (content instanceof String) {
                builder.append(content);
            } else if (content instanceof InputStream) {
                builder.append("\n").append("[Binary data]").append("\n");
            } else if (content instanceof Message) {
                handleMessage((Message) content, builder);
            } else if (content instanceof Multipart) {
                handleMultipart((Multipart) content, builder);
            }
        }
    }
}
