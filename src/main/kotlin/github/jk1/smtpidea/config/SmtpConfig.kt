package github.jk1.smtpidea.config

/**
 *
 * @author Evgeny Naumenko
 */
public class SmtpConfig : ServerConfig(){

    public enum class AuthType {
        DISABLED
        SUPPORTED
        ENFORCED
    }

    public enum class TransportSecurity {
        PLAINTEXT
        STARTTLS_SUPPORTED
        STARTTLS_ENFORCED
        SSL
    }

    public var port: Int = 25;
    public var authType: AuthType = AuthType.DISABLED;
    public var transportSecurity: TransportSecurity = TransportSecurity.PLAINTEXT;
    public var login: String = "";
    public var password: String = "";
}