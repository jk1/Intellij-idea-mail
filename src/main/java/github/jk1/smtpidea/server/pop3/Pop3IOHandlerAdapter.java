package github.jk1.smtpidea.server.pop3;

import java.util.List;

import github.jk1.smtpidea.server.base.MailResourceFactory;
import github.jk1.smtpidea.server.util.Event;
import github.jk1.smtpidea.server.util.Filter;
import github.jk1.smtpidea.server.util.FilterChain;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;

/**
 * @author Evgeny Naumenko
 */
public class Pop3IOHandlerAdapter extends IoHandlerAdapter {


    private MailResourceFactory resourceFactory;
    private final List<Filter> filters;

    public Pop3IOHandlerAdapter(MailResourceFactory resourceFactory, List<Filter> filters) {
        this.resourceFactory = resourceFactory;
        this.filters = filters;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable t) throws Exception {
        t.printStackTrace();
        session.close();
    }

    @Override
    public void messageReceived(final IoSession session, final Object msg) throws Exception {
        Pop3MessageEvent event = new Pop3MessageEvent(session, msg);
        Filter terminal = new Filter() {

            public void doEvent(FilterChain chain, Event event) {
                Pop3Server.sess(session).messageReceived(session, msg);
            }
        };
        FilterChain chain = new FilterChain(filters, terminal);
        chain.doEvent(event);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        ((SocketSessionConfig) session.getConfig()).setReceiveBufferSize(2048);
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        Pop3Session sess = new Pop3Session(session, resourceFactory);
        session.setAttribute("stateMachine", sess);
    }
}