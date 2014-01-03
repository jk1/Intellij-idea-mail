package github.jk1.smtpidea.server.pop3

import org.junit.Test
import org.junit.After
import github.jk1.smtpidea.config.Pop3Config
import org.junit.Before
import javax.mail.Folder
import javax.mail.Session
import java.util.Properties
import kotlin.test.assertEquals

class Pop3ServerTest : TestUtils{

    val login = "user"
    val password = "secret"

    var config: Pop3Config = Pop3Config()
    var server: Pop3Server = Pop3Server(config)
    var folder: Folder? = null

    Before fun setUp() {
        config = Pop3Config();
        config.authLogin = login
        config.authPassword = password
        config.port = findFreePort()
        server = Pop3Server(config);
        server.start()
        println("Server started on port ${config.port}")
    }

    Test fun testEmptyMailbox() {
        connectToFolder()

        val messages = folder?.getMessages();

        assertEquals(0, messages?.size)
    }

    fun connectToFolder() {
        val session = Session.getInstance(Properties());
        val store = session?.getStore("pop3");
        store?.connect("127.0.0.1", config.port, login, password);
        folder = store?.getFolder("inbox");
        folder?.open(Folder.READ_ONLY);
    }

    After fun tearDown() {
        folder?.close(false);
        server.stop()
    }
}
