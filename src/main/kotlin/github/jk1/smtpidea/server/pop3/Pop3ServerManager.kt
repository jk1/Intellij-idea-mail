package github.jk1.smtpidea.server.pop3

import github.jk1.smtpidea.config.Pop3Config
import github.jk1.smtpidea.server.ServerManager

/**
 *
 */
public class Pop3ServerManager : ServerManager<Pop3Config>{

    override var running: Boolean = false
    override var configuration: Pop3Config = Pop3Config()
    private var server : Pop3Server? = null

    override fun startServer() {
        try {
            if (running){
                server?.stop()
            }
            server = Pop3Server(configuration)
            server?.start()
            running = true
        }  catch(e: Exception) {
            e.printStackTrace()
            notifyFailure("${e.getMessage()}")
        }
    }

    override fun stopServer() {
        try {
            server?.stop()
            running = false
        } catch(e: Exception) {
            e.printStackTrace()
            notifyFailure("${e.getMessage()}")
        }
    }
}