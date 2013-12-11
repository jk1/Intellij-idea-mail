package github.jk1.smtpidea.server.pop3;

import org.apache.mina.core.session.IoSession;

/**
 * @author Evgeny Naumenko
 */
public interface PopState {
    void enter(IoSession session, Pop3Session popSession);

    void exit(IoSession session, Pop3Session popSession);
}