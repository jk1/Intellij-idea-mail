package github.jk1.smtpidea.server.base;

import javax.mail.internet.MimeMessage;

/**
 * @author Evgeny Naumenko
 */
public interface Mailbox {

    public boolean authenticate(String password);

    public boolean authenticateMD5(byte[] passwordHash);

    public MessageFolder getInbox();

    public MessageFolder getMailFolder(String name);

    /**
     *
     * @return - true iff this mailbox is enabled for sending and receiving emails
     */
    public boolean isEmailDisabled();

    public void storeMail(MimeMessage mm);

}
