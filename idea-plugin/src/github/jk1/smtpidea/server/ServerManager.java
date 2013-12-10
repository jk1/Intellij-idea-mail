package github.jk1.smtpidea.server;

import github.jk1.smtpidea.server.smtp.ServerConfiguration;

/**
 * @author Evgeny Naumenko
 */
public interface ServerManager {


    void setConfiguration(ServerConfiguration configuration);

    void startServer();

    void stopServer();

    boolean isRunning();
}
