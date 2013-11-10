package github.jk1.smtpidea.server;

/**
 *
 * @author Evgeny Naumenko
 */
public class ServerConfiguration {

    public int port;

    /**
     * For serialization purposes only
     */
    public ServerConfiguration() {
    }

    public ServerConfiguration(int port) {
        this.port = port;
    }
}
