package github.jk1.smtpidea.server.pop3

import java.net.ServerSocket

public trait TestUtils{

    public fun findFreePort() : Int {
        val socket = ServerSocket(0)
        val port = socket.getLocalPort()
        socket.close()
        return port
    }
}
