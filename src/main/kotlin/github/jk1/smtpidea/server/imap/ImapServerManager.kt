package github.jk1.smtpidea.server.imap

import github.jk1.smtpidea.server.ServerManager
import github.jk1.smtpidea.config.Pop3Config

/**
 *
 */
public class ImapServerManager : ServerManager<Pop3Config>{

    override var running: Boolean = false

    override fun start() {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun stop() {
        throw UnsupportedOperationException("Not implemented")
    }
}