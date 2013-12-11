package github.jk1.smtpidea.server.pop3;

import org.apache.mina.core.session.IoSession;

/**
 * @author Evgeny Naumenko
 */
public abstract class BaseState implements PopState {

    protected Pop3Session popSession;

    public BaseState(Pop3Session popSession) {
        super();
        this.popSession = popSession;
    }

    abstract void capa(IoSession session, Pop3Session popSession, String[] args);

    public void quit(IoSession session, Pop3Session popSession, String[] args) {
        popSession.transitionTo(session, new UpdateState(popSession));
    }
}