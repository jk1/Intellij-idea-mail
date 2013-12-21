package github.jk1.smtpidea.server.smtp;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * TLS-secured SMTP server implementation.
 * It requires client connections to start with a handshake.
 *
 * @author Evgeniy Naumenko
 */
class SmtpsMailServer extends SmtpMailServer {

    /**
     * @param configuration
     */
    public SmtpsMailServer(ServerConfiguration configuration) {
        super(configuration);
        this.setEnableTLS(false); // disable STARTTLS, it makes no sense here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ServerSocket createServerSocket() throws IOException {
        InetSocketAddress isa = new InetSocketAddress(this.getBindAddress(), this.getPort());
        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ServerSocket serverSocket = factory.createServerSocket(this.getPort(), this.getBacklog(), isa.getAddress());
        if (this.getPort() == 0) {
            this.setPort(serverSocket.getLocalPort());
        }
        return serverSocket;
    }
}