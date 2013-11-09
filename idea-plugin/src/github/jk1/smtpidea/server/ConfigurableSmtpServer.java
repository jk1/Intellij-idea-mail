package github.jk1.smtpidea.server;

/**
 *
 */
public interface ConfigurableSmtpServer {

    void setConfiguration(ServerConfiguration configuration);

    void startServer();

    void stopServer();

    boolean isRunning();
}
