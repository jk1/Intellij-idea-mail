package github.jk1.smtpidea.server.smtp

import github.jk1.smtpidea.server.ServerManager
import org.subethamail.smtp.server.SMTPServer
import github.jk1.smtpidea.config.SmtpConfig

/**
 *
 */
public class SmtpServerManager : ServerManager<SmtpConfig> {

    private var server: SMTPServer? = null
    override var running: Boolean = false
    override var configuration: SmtpConfig = SmtpConfig()

    public override fun startServer() {
        if (configuration.transportSecurity == SmtpConfig.TransportSecurity.SSL) {
            server = SmtpsMailServer(configuration)
        } else {
            server = SmtpMailServer(configuration)
        }
        try  {
            server?.start()
            running = true
        } catch (e: Exception) {
            e.printStackTrace()
            notifyFailure("${e.getMessage()}")
        }

    }
    public override fun stopServer() {
        try {
            server?.stop()
            running = false
        } catch (e: Exception) {
            e.printStackTrace()
            notifyFailure("${e.getMessage()}")
        }
    }
}