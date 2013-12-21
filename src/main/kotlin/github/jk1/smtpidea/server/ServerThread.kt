package github.jk1.smtpidea.server

import java.net.ServerSocket
import java.util.concurrent.Semaphore
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.io.IOException
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.TimeUnit
import java.util.HashSet
import java.net.Socket
import github.jk1.smtpidea.server.MailSession
import github.jk1.smtpidea.server.pop3.Pop3Server

/**
 *
 * Should be kept in this strange package as superclass has package visibility in subetha
 *
 * @author Evgeny Naumenko
 */
public abstract class ServerThread(val serverSocket: ServerSocket) : Thread() {

    /**
     * A semaphore which is used to prevent accepting new connections by
     * blocking this thread if the allowed count of open connections is already
     * reached.
     */
    private val connectionPermits: Semaphore = Semaphore(5)
    /**
     * The list of currently running sessions.
     */
    private val sessionThreads: MutableSet<MailSession> = Collections.newSetFromMap(ConcurrentHashMap())

    /**
     * A flag which indicates that this port and all of its open
     * connections are being shut down.
     */
    private volatile var shuttingDown: Boolean = false

    /**
     * This method is called by this thread when it starts up. To safely cause
     * this to exit, call {@link #shutdown()}.
     */
    public override fun run() {
        while (!this.shuttingDown) {
            try {
                // block if too many connections are open
                connectionPermits.acquire()
                val socket = this.serverSocket.accept()
                val session = this.createSession(socket)
                try {
                    // add thread before starting it,
                    // because it will check the count of sessions
                    sessionThreads.add(session)
                    Pop3Server.getExecutorService()?.execute(session)
                } catch(e: IOException) {
                    socket.close();
                    throw e;
                } catch (e: RejectedExecutionException) {
                    sessionThreads.remove(session)
                    socket.close()
                    throw e;
                }
            } catch (e: Exception) {
                connectionPermits.release()
                // it also happens during shutdown, when the socket is closed
                if (!this.shuttingDown) {
                    // prevent a possible loop causing 100% processor usage
                    Thread.sleep(1000)
                }
            }
        }
    }

    public abstract fun createSession(socket: Socket): MailSession

    /**
     * Closes the server socket and all client sockets.
     */
    public open fun shutdown() {
        // First make sure we aren't accepting any new connections
        shutdownServerThread()
        // Shut down any open connections.
        shutdownSessions()
    }
    private fun shutdownServerThread() {
        shuttingDown = true
        this.serverSocket.close()
        this.interrupt()
        try {
            this.join()
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    private fun shutdownSessions() {
        for (sessionThread in HashSet(sessionThreads)) {
            sessionThread.quit()
        }
        Pop3Server.getExecutorService()?.shutdown()
        try {
            Pop3Server.getExecutorService()?.awaitTermination(java.lang.Long.MAX_VALUE, TimeUnit.NANOSECONDS)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    /**
     * Registers that the specified {@link Session} thread ended. Session
     * threads must call this function.
     */
    public fun sessionEnded(session: Any) {
        sessionThreads.remove(session)
        connectionPermits.release()
    }

}
