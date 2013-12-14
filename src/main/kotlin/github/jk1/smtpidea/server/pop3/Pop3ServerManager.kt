package github.jk1.smtpidea.server.pop3

import github.jk1.smtpidea.config.Pop3Config
import github.jk1.smtpidea.ServerManager

/**
 *
 *
 * @author Evgeny Naumenko
 */
public class Pop3ServerManager : ServerManager<Pop3Config>{

    override fun setConfiguration(configuration: Pop3Config) {
    }

    override fun startServer() {
    }
    override fun stopServer() {
    }
    override fun isRunning(): Boolean {
        return false;
    }
}