package github.jk1.smtpidea.server;

/**
 * @author Evgeny Naumenko
 */
public class ServerConfiguration {

    public static enum AuthType {DISABLED, SUPPORTED, ENFORCED}

    public static enum TransportSecurity {PLAINTEXT, STARTTLS_SUPPORTED, STARTTLS_ENFORCED, SSL}

    public int port = 25;
    public AuthType authType = AuthType.DISABLED;
    public TransportSecurity transportSecurity =  TransportSecurity.PLAINTEXT;
    public String login = "";
    public String password = "";
}
