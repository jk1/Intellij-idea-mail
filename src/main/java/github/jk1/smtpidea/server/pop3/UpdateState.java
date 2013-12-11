package github.jk1.smtpidea.server.pop3;

import github.jk1.smtpidea.server.base.Message;
import org.apache.mina.core.session.IoSession;

/**
 * @author Evgeny Naumenko
 */
public class UpdateState implements PopState {

    private Pop3Session popSession;

    public UpdateState(Pop3Session popSession) {
        super();
        this.popSession = popSession;
    }

    public void enter(IoSession session, Pop3Session popSession) {
        if(popSession.getMessages() != null) {
            for (Message m : popSession.getMessages()) {
                if (m.isMarkedForDeletion()) {
                    m.getResource().deleteMessage();
                }
            }
        }
        popSession.reply(session, "+OK");
        session.close();
    }

    public void exit(IoSession session, Pop3Session popSession) {
    }
}