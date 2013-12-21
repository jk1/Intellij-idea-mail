package github.jk1.smtpidea.server.pop3

import java.net.Socket
import java.io.InputStream
import org.subethamail.smtp.io.CRLFTerminatedReader
import java.io.PrintWriter
import org.subethamail.smtp.AuthenticationHandler
import java.io.IOException
import java.util.UUID
import github.jk1.smtpidea.server.ServerThread
import java.net.SocketException
import org.subethamail.smtp.DropConnectionException
import java.net.SocketTimeoutException
import java.net.InetSocketAddress
import github.jk1.smtpidea.config.Pop3Config
import org.subethamail.smtp.server.CommandException
import github.jk1.smtpidea.server.MailSession
import javax.mail.internet.MimeMessage

/**
 * The thread that handles a connection with current protocol session state.
 * This class passes most of it's responsibilities off to the CommandHandler.
 *
 * @author Evgeny Naumenko
 */
public class Pop3Session(val serverThread: ServerThread, var socket: Socket) : MailSession {

    public var config: Pop3Config? = null
    public var authenticationHandler: AuthenticationHandler? = null

    /** Session state */
    private var quitting: Boolean = false
    public val sessionId: String = "<${UUID.randomUUID().toString()}@intellij.idea>";
    public var tlsStarted: Boolean = false
    public var authenticated: Boolean = false
    public var username: String? = null

    /** I/O to the client */
    private var input: InputStream? = null
    private var reader: CRLFTerminatedReader? = null
    private var writer: PrintWriter? = null

    {
        this.switchToSocket(socket)
    }

    public override fun run() {
        try {
            this.runCommandLoop()
        } catch (e: Exception) {
            if (!this.quitting) {
                this.writeErrorResponseLine("${e.getMessage()}")
            }
        } finally {
            this.closeConnection()
            serverThread.sessionEnded(this)
        }
    }

    /**
     * Sends the welcome message and starts receiving and processing client
     * commands. It quits when {@link #quitting} becomes true or when it can be
     * noticed or at least assumed that the client no longer sends valid
     * commands, for example on timeout.
     *
     * @throws IOException
     *             if sending to or receiving from the client fails.
     */
    private fun runCommandLoop() {
        this.writeOkResponseLine("${Pop3Server.getSoftwareName()} is ready $sessionId")
        while (!this.quitting) {
            try {
                try {
                    val line = this.reader?.readLine()
                    if (line != null) {
                        CommandHandler.handle(line, this)
                    }
                } catch (ex: SocketException) {
                    // Lots of clients just "hang up" rather than issuing QUIT,
                    return
                }
            } catch (ex: CommandException){
                this.writeErrorResponseLine("${ex.getMessage()}")
            } catch (ex: DropConnectionException) {
                this.writeErrorResponseLine("Closing connection: ${ex.getErrorResponse()}")
                return
            } catch (ex: SocketTimeoutException) {
                this.writeErrorResponseLine("Closing connection: timeout waiting for data from client.")
                return
            } catch (te: CRLFTerminatedReader.TerminationException) {
                this.writeErrorResponseLine("Closing connection: syntax error at position ${te.position()}, CR and LF must be paired")
                return
            } catch (mlle: CRLFTerminatedReader.MaxLineLengthException) {
                this.writeErrorResponseLine("Closing connection: line too long")
                return
            }
        }
    }

    /**
     * Close reader, writer, and socket, logging exceptions but otherwise ignoring them
     */
    private fun closeConnection() {
        try {
            try {
                writer?.close();
                input?.close();
            } finally {
                with(socket) {
                    if (isBound() && !isClosed()) close()
                }
            }
        } catch (e: IOException) {
            // ignore
        }
    }
    /**
     * Initializes our reader, writer, and the i/o filter chains based on
     * the specified socket.  This is called internally when we startup
     * and when (if) SSL is started.
     */
    public fun switchToSocket(socket: Socket) {
        this.socket = socket
        input = socket.getInputStream()
        reader = CRLFTerminatedReader(input)
        writer = PrintWriter(socket.getOutputStream()!!)  // http://youtrack.jetbrains.com/issue/KT-4322
        socket.setSoTimeout(Pop3Server.getConnectionTimeout())
    }
    /**
     * This method is only used by the start tls command
     * @return the current socket to the client
     */
    public fun getSocket(): Socket {
        return socket
    }

    public fun writeOkResponseLine(response: String = "") : Unit = this.writeResponseLine("+OK $response")

    public fun writeErrorResponseLine(response: String = "") : Unit = this.writeResponseLine("-ERR $response")

    override fun writeResponseLine(response: String) {
        writer?.print("$response\r\n")
        writer?.flush()
    }

    public fun writeResponseMessage(response: MimeMessage) : Unit =  response.writeTo(socket.getOutputStream())

    public fun getRemoteAddress(): InetSocketAddress = socket.getRemoteSocketAddress() as InetSocketAddress

    override fun reset() {
        authenticated = false
        username = null
    }

    /**
     * Triggers the shutdown of the thread and the closing of the connection.
     */
    public override fun quit() {
        this.quitting = true
        this.closeConnection()
    }
}
