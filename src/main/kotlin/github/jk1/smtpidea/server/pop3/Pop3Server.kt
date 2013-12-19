package github.jk1.smtpidea.server.pop3

import org.subethamail.smtp.server.SMTPServer
import java.net.ServerSocket
import org.subethamail.smtp.server.ServerThread
import github.jk1.smtpidea.server.MailSession
import java.net.Socket
import github.jk1.smtpidea.server.Authenticator
import github.jk1.smtpidea.config.Pop3Config


/**
 * @author Evgeny Naumenko
 */
public object Pop3Server : SMTPServer(null) {

    private var serverThread: ServerThread? = null
    public var authenticator: Authenticator? = null
    public var config: Pop3Config = Pop3Config();
    private var started: Boolean = false

    {
        setDisableReceivedHeaders(true)
        setSoftwareName("Intellij Idea POP3 Server")
    }

    /**
     * Call this method to get things rolling after instantiating the server.
     */
    public override fun start() {
        if (this.started)
            throw IllegalStateException("Server is already started")
        val serverSocket: ServerSocket? = this.createServerSocket()
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

    class Pop3ServerThread(socket: ServerSocket) : ServerThread(socket) {
        override fun createSession(socket: Socket): MailSession {
            return Pop3Session(this, socket)
        }
    }
}
