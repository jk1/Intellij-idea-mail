package github.jk1.smtpidea.server;

import github.jk1.smtpidea.config.ServerConfig;
import github.jk1.smtpidea.server.smtp.ServerConfiguration;

/**
 * @author Evgeny Naumenko
 */
public interface ServerManager<T extends ServerConfig> {

    void setConfiguration(T configuration);

    void startServer();

    void stopServer();

    boolean isRunning();
}
