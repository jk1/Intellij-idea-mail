package github.jk1.smtpidea.server.smtp;

import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.auth.EasyAuthenticationHandlerFactory;
import org.subethamail.smtp.auth.LoginFailedException;
import org.subethamail.smtp.auth.UsernamePasswordValidator;
import org.subethamail.smtp.server.SMTPServer;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * SMTP mail server implementation.
 * Basically, we can launch any number of these servers on different ports.
 * <p> Call start() to launch the server and start listening for connections
 * <p> Call stop() to disable the server (it's not supposed to be started again)
 *
 * @author Evgeny Naumenko
 */
class SmtpMailServer extends SMTPServer {

    private ServerConfiguration configuration;

    /**
     * @param configuration
     */
    public SmtpMailServer(ServerConfiguration configuration) {
        super(new IncomingMailHandlerFactory());
        this.configuration = configuration;
        this.setPort(configuration.port);
        this.setupAuthentication();
        this.setupStarttls();
        this.setSoftwareName("Intellij Idea Server");
        /*
         Disable "Received:" header construction. It involves network activity
         and may take significant amount of time in some networks.
         */
        this.setDisableReceivedHeaders(true);
    }

    private void setupAuthentication() {
        if (configuration.authType != ServerConfiguration.AuthType.DISABLED) {
            this.setAuthenticationHandlerFactory(
                    new EasyAuthenticationHandlerFactory(new CredentialsValidator()));
            if (configuration.authType == ServerConfiguration.AuthType.ENFORCED) {
                this.setRequireAuth(true);
            }
        }
    }

    private void setupStarttls() {
        if (configuration.transportSecurity == ServerConfiguration.TransportSecurity.STARTTLS_SUPPORTED) {
            this.setEnableTLS(true);
        }
        if (configuration.transportSecurity == ServerConfiguration.TransportSecurity.STARTTLS_ENFORCED) {
            this.setEnableTLS(true);
            this.setRequireTLS(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SSLSocket createSSLSocket(Socket socket) throws IOException {
        InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket s = (SSLSocket) sf.createSocket(socket, remoteAddress.getHostName(), socket.getPort(), true);
        // we are the server
        s.setUseClientMode(false);
        return s;
    }

    /**
     * Captures all incoming e-mails and forwards it into MailStoreComponent implementation
     */
    private static class IncomingMailHandlerFactory implements MessageHandlerFactory {

        @Override
        public MessageHandler create(MessageContext ctx) {
            return new MailSession(ctx);
        }
    }

    /**
     * Authentication provider, simply checks login/password pair to match predefined values
     */
    private class CredentialsValidator implements UsernamePasswordValidator {
        @Override
        public void login(String username, String password) throws LoginFailedException {
            if (!username.equals(configuration.login) || !password.equals(configuration.password)) {
                throw new LoginFailedException();
            }
        }
    }
}