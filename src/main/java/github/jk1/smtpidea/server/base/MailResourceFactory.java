package github.jk1.smtpidea.server.base;

import javax.mail.internet.InternetAddress;

/**
 * @author Evgeny Naumenko
 */
public interface MailResourceFactory {

    public Mailbox getMailbox(InternetAddress add);

}
