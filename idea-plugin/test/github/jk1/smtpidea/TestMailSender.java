package github.jk1.smtpidea;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 */
public class TestMailSender {
    public static void main(String[] args) throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "localhost");
        properties.setProperty("mail.smtp.port", "25");
        Session session = Session.getDefaultInstance(properties);
        SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
        MimeMessage message = new MimeMessage(session);
        message.setText("test");
        transport.connect();
        transport.sendMessage(message, new Address[]{new InternetAddress("lol@wut")});
    }
}
