package github.jk1.smtpidea.config

/**
 * POP3 server persistent configuration POJO
 */
public class Pop3Config : ServerConfig() {

    public var port : Int = 110

    public var serverName : String = "Intellij Idea POP3 Server"

    public var removalSupported : Boolean = false;

    /**
     * If server should inform client about effective mail expiration policy.
     * This is merely an emulation, as plugin manages no real mailboxes.
     *
     * @see RFC 2449
     */
    public var expirationEnabled: Boolean = true

    /**
     * Describes how many days message may live on server in accordance to it's
     * mail expiration policy. 0 means infinite period. This option has no effect
     * if expiration is disabled at all.
     *
     * @see RFC 2449
     */
    public var expiresDays : Int = 0

    public var authLogin : String = "user"

    public var authPassword : String = "secret"
}