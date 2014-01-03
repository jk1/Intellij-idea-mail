package github.jk1.smtpidea.config

import java.util.concurrent.TimeUnit

/**
 *
 */
public abstract class ServerConfig{

    /**
     * If server should be automatically launched on project load
     */
    public var launchOnStartup: Boolean = false

    public val connectionTimeout : Int = 60000 // 1 minute

}