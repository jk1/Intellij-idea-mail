package github.jk1.smtpidea.server.pop3;

import github.jk1.smtpidea.server.base.MailResourceFactory;
import github.jk1.smtpidea.server.util.Filter;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.stream.StreamWriteFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @author Evgeny Naumenko
 */
public class Pop3Server  {


    private SocketAcceptor acceptor;
    private int popPort;
    private MailResourceFactory resourceFactory;
    private final List<Filter> filters;

    public Pop3Server(MailResourceFactory resourceFactory, List<Filter> filters) {
        this(110, resourceFactory, filters);
    }

    public Pop3Server(int popPort, MailResourceFactory resourceFactory, List<Filter> filters) {
        this.popPort = popPort;
        this.resourceFactory = resourceFactory;
        this.filters = filters;
    }

    public Pop3Server(int popPort, MailResourceFactory resourceFactory, Filter filter) {
        this( popPort, resourceFactory, Arrays.asList(filter));
    }



    public void start() {
        IoBuffer.setUseDirectBuffer(false);
        IoBuffer.setAllocator(new SimpleBufferAllocator());

        acceptor = new NioSocketAcceptor();

        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("US-ASCII"))));
        acceptor.getFilterChain().addLast("stream", new StreamWriteFilter() );
        acceptor.setHandler( new Pop3IOHandlerAdapter(resourceFactory, filters) );
        try {
            //cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
            acceptor.bind(new InetSocketAddress(popPort));
        } catch (IOException ex) {
            throw new RuntimeException("Couldnt bind to port: " + popPort, ex);
        }

    }

    public void stop() {
        acceptor.unbind();
        acceptor = null;
    }

    public int getPopPort() {
        return popPort;
    }

    public void setPopPort(int popPort) {
        this.popPort = popPort;
    }

    public MailResourceFactory getResourceFactory() {
        return resourceFactory;
    }

    public void setResourceFactory(MailResourceFactory resourceFactory) {
        this.resourceFactory = resourceFactory;
    }



    static Pop3Session sess(IoSession session) {
        return (Pop3Session) session.getAttribute("stateMachine");
    }

}