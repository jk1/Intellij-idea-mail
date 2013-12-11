package github.jk1.smtpidea.server.pop3;

import github.jk1.smtpidea.server.base.Mailbox;
import org.apache.mina.core.session.IoSession;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Evgeny Naumenko
 */
public class AuthState extends BaseState {

    private String user;
    private String pass;
    private Mailbox mbox;

    public AuthState(Pop3Session popSession) {
        super(popSession);
    }

    public void enter(IoSession session, Pop3Session popSession) {
    }

    public void exit(IoSession session, Pop3Session popSession) {
    }

    public void capa(IoSession session, Pop3Session popSession, String[] args) {
        popSession.reply(session, "+OK Capability list follows");
        popSession.reply(session, "USER");
        popSession.reply(session, ".");
    }

    public void user(IoSession session, Pop3Session popSession, String[] args) {
        user = args[1];
        InternetAddress add;
        try {
            add = new InternetAddress(user);
            mbox = popSession.getResourceFactory().getMailbox(add);
            if (mbox != null) {
                popSession.reply(session, "+OK");
            } else {
                popSession.reply(session, "-ERR");
            }
        } catch (AddressException ex) {
            popSession.reply(session, "-ERR Could not parse user name. Use form: user@domain.com");
        }
    }

    public void pass(IoSession session, Pop3Session popSession, String[] args) {
        if( args.length > 1 ) {
            pass = args[1];
        } else {
            pass = "";
        }
        if (mbox == null) {
            popSession.reply(session, "-ERR");
        } else {
            if (mbox.authenticate(pass)) {
                popSession.reply(session, "+OK Mailbox locked and ready SESSIONID=<slot111-3708-1233538479-1>");
                // s("+OK Mailbox locked and ready SESSIONID=<slot111-3708-1233538479-1>\015\012");

                popSession.setAuth(this);
                popSession.transitionTo(session, new TransactionState(popSession));
            } else {
                popSession.reply(session, "-ERR");
                popSession.setAuth(null);
            }
        }
    }

    public void apop(IoSession session, Pop3Session popSession, String[] args) {
        user = args[1];
        InternetAddress add;
        try {
            add = new InternetAddress(user);
            mbox = popSession.getResourceFactory().getMailbox(add);
            if (mbox != null) {
                String md5Pass = args[2];
                if (mbox.authenticateMD5(md5Pass.getBytes())) {
                    popSession.reply(session, "+OK");
                    popSession.setAuth(this);
                    popSession.transitionTo(session, new TransactionState(popSession));
                } else {
                    popSession.reply(session, "-ERR authentication failed");
                }
            } else {
                popSession.reply(session, "-ERR mailbox not found");
            }
        } catch (AddressException ex) {
            popSession.reply(session, "-ERR Could not parse user name. Use form: user@domain.com");
        }
    }

    public void auth(IoSession session, Pop3Session popSession, String[] args) {
        popSession.reply(session, "-ERR not supported");
    }


    public Mailbox getMbox() {
        return mbox;
    }
}
