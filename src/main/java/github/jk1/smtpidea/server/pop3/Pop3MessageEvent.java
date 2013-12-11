package github.jk1.smtpidea.server.pop3;

import github.jk1.smtpidea.server.util.Event;
import org.apache.mina.core.session.IoSession;

/**
 * @author Evgeny Naumenko
 */
public class Pop3MessageEvent implements Event {
    final private IoSession session;
    private final Object msg;

    public Pop3MessageEvent(IoSession session, Object msg) {
        this.session = session;
        this.msg = msg;
    }

    public Object getMsg() {
        return msg;
    }

    public IoSession getSession() {
        return session;
    }
}