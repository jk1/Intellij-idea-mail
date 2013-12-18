package github.jk1.smtpidea.server.pop3

import github.jk1.smtpidea.config.Pop3Config
import github.jk1.smtpidea.ServerManager

/**
 *
 *
 * @author Evgeny Naumenko
 */
public class Pop3ServerManager : ServerManager<Pop3Config>{

    override var running: Boolean = false
    override var configuration: Pop3Config = Pop3Config()

    override fun startServer() {
        try {
            Pop3Server.start()
        }  catch(e: Exception) {
            e.printStackTrace()
            this.notifyFailure("${e.getMessage()}")
        }
    }

    override fun stopServer() {
        try {
            Pop3Server.stop()
        } catch(e: Exception) {
            e.printStackTrace()
            this.notifyFailure("${e.getMessage()}")
        }
    }
}