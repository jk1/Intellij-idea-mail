package github.jk1.smtpidea.server.pop3

import github.jk1.smtpidea.server.ServerManager
import github.jk1.smtpidea.server.smtp.ServerConfiguration
import github.jk1.smtpidea.config.ServerConfig
import github.jk1.smtpidea.config.Pop3Config

/**
 *
 *
 * @author Evgeny Naumenko
 */
public class Pop3ServerManager : ServerManager<Pop3Config>{

    override fun setConfiguration(configuration: Pop3Config?) {
        throw UnsupportedOperationException()
    }
    override fun startServer() {
        throw UnsupportedOperationException()
    }
    override fun stopServer() {
        throw UnsupportedOperationException()
    }
    override fun isRunning(): Boolean {
        throw UnsupportedOperationException()
    }
}