package github.jk1.smtpidea.server.pop3;

import github.jk1.smtpidea.server.base.MailResourceFactory;
import github.jk1.smtpidea.server.base.Message;
import org.apache.mina.core.session.IoSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Evgeny Naumenko
 */
public class Pop3Session {

    private UUID sessionId;
    private PopState state;
    private AuthState auth;
    private final MailResourceFactory resourceFactory;
    private Collection<Message> messageResources;
    private ArrayList<Message> messages;

    Pop3Session(IoSession session, final MailResourceFactory resourceFactory) {
        super();
        sessionId = UUID.randomUUID();
        this.resourceFactory = resourceFactory;
        state = new GreetingState(this);
        state.enter(session, this);

    }

    void messageReceived(IoSession session, Object msg) {
        try {
            String sMsg = (String) msg;
            String[] arr = sMsg.split(" ");
            String sCmd = arr[0];
            sCmd = sCmd.toLowerCase();
            Method m = state.getClass().getMethod(sCmd, IoSession.class, Pop3Session.class, arr.getClass());
            m.invoke(state, session, this, arr);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    void transitionTo(IoSession session, PopState newState) {
        state.exit(session, this);
        state = newState;
        state.enter(session, this);
    }

    void reply(IoSession session, String msg) {
        session.write(msg + (char) 13);
    }

    void replyMultiline(IoSession session, String content) {
        // todo: handle special case of single line consisting of a .
        session.write(content + "\n");
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public MailResourceFactory getResourceFactory() {
        return resourceFactory;
    }

    public AuthState getAuth() {
        return auth;
    }

    public void setAuth(AuthState auth) {
        this.auth = auth;
    }
}
