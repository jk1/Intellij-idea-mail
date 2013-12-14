package github.jk1.smtpidea.server.imap

import github.jk1.smtpidea.ServerManager
import github.jk1.smtpidea.config.Pop3Config

/**
 *
 */
public class ImapServerManager : ServerManager<Pop3Config>{

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