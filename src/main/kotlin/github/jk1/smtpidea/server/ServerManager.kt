package github.jk1.smtpidea

import github.jk1.smtpidea.config.ServerConfig

/**
 *
 */
/**
 * @author Evgeny Naumenko
 */
public trait ServerManager<T : ServerConfig> {
    fun setConfiguration(configuration : T) : Unit
    fun startServer() : Unit
    fun stopServer() : Unit
    fun isRunning() : Boolean
}
