package github.jk1.smtpidea.server.pop3;


import org.apache.mina.core.session.IoSession;

/**
 * @author Evgeny Naumenko
 */
public class GreetingState implements PopState {

    private Pop3Session popSession;

    public GreetingState(Pop3Session popSession) {
        super();
        this.popSession = popSession;
    }

    public void enter(IoSession session, Pop3Session popSession) {
        popSession.reply(session, "+OK POP3 ready"); //todo
        popSession.transitionTo(session, new AuthState(popSession));
    }

    public void exit(IoSession session, Pop3Session popSession) {
    }

    public void messageReceived(IoSession session, Object msg, Pop3Session popSession) {
    }
}