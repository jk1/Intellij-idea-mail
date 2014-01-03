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

    var config: Pop3Config = Pop3Config()
    var server: Pop3Server = Pop3Server(config)
    var folder : Folder? = null

    Before fun setUp() {
        config = Pop3Config();
        config.port = findFreePort()
        server = Pop3Server(config);
        server.start()
        println("Server started on port ${config.port}")
    }

    Test fun testEmptyMailbox() {
        val folder = connectToFolder()

        val messages = folder.getMessages();

        assertEquals(0, messages?.size)
    }

    fun connectToFolder(): Folder {
        val session = Session.getDefaultInstance(Properties());
        val store = session?.getStore("pop3");
        store?.connect("127.0.0.1", config.port, "test", "test");
        val folder = store?.getFolder("inbox");
        folder?.open(Folder.READ_ONLY);
        return folder!!;
    }

    After fun tearDown() = server.stop()
}
