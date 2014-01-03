package github.jk1.smtpidea.server.pop3

import org.subethamail.smtp.server.SMTPServer
import java.net.ServerSocket
import github.jk1.smtpidea.server.ServerThread
import github.jk1.smtpidea.server.MailSession
import java.net.Socket
import github.jk1.smtpidea.server.Authenticator
import github.jk1.smtpidea.config.Pop3Config

/**
 *
 */
public class Pop3Server(val config: Pop3Config) : SMTPServer(null) {

    private var serverThread: ServerThread? = null
    public var authenticator: Authenticator? = null
    private var started: Boolean = false

    {
        setPort(config.port)
        setDisableReceivedHeaders(true)
    }

    /**
     * Call this method to get things rolling after instantiating the server.
     */
    public override fun start() {
        if (started)
            throw IllegalStateException("Server is already started")
        val serverSocket: ServerSocket? = createServerSocket()
        if (serverSocket != null) {
            serverThread = Pop3ServerThread(serverSocket)
            serverThread?.start()
            started = true
        }
    }

    override fun stop() {
        serverThread?.shutdown()
        serverThread = null
        started = false
    }

    inner class Pop3ServerThread(socket: ServerSocket) : ServerThread(socket, this@Pop3Server) {
        override fun createSession(socket: Socket): MailSession {
            return Pop3Session(this, socket, config)
        }
    }
}
