package github.jk1.smtpidea.server.imap;

import github.jk1.smtpidea.server.ServerManager;
import github.jk1.smtpidea.server.smtp.ServerConfiguration;

/**
 * @author Evgeny Naumenko
 */
public class ImapServerManager implements ServerManager {

    @Override
    public void setConfiguration(ServerConfiguration configuration) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void startServer() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void stopServer() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isRunning() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
