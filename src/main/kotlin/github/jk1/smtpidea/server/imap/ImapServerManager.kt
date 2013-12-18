package github.jk1.smtpidea.server.imap

import github.jk1.smtpidea.ServerManager
import github.jk1.smtpidea.config.Pop3Config

/**
 *
 */
public class ImapServerManager : ServerManager<Pop3Config>{
    override var configuration: Pop3Config = Pop3Config()
    override var running: Boolean = false

    override fun startServer() {
    }
    override fun stopServer() {
    }
}