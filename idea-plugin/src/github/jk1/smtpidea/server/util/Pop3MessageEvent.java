package github.jk1.smtpidea.server.util;

import org.apache.mina.core.session.IoSession;

/**
 * @author Evgeny Naumenko
 */
public class Pop3MessageEvent implements Event {

    private final  IoSession session;
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