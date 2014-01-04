package github.jk1.smtpidea.server.pop3

import org.junit.Test
import org.junit.After
import github.jk1.smtpidea.config.Pop3Config
import org.junit.Before
import javax.mail.Folder
import javax.mail.Session
import java.util.Properties
import kotlin.test.assertEquals
import javax.mail.AuthenticationFailedException
import github.jk1.smtpidea.store.InboxFolder
import javax.mail.internet.MimeMessage

/**
 * Integration test for POP3 server
 */
class Pop3ServerTest : TestUtils{

    val login = "user"
    val password = "secret"

    var config: Pop3Config = Pop3Config()
    var server: Pop3Server = Pop3Server(config)

    Before fun setUp() {
        InboxFolder.clear()
        config = Pop3Config();
        config.authLogin = login
        config.authPassword = password
        config.port = findFreePort()
        server = Pop3Server(config);
        server.start()
    }

    Test fun testEmptyMailbox() {
        val folder = connectToFolder()
        val messages = folder.getMessages();
        assertEquals(0, messages?.size)
    }

    Test fun testMessageCount() {
        InboxFolder.add(createMimeMessage("content"))
        InboxFolder.add(createMimeMessage("content"))
        InboxFolder.add(createMimeMessage("content"))
        val folder = connectToFolder()
        val messages = folder.getMessages();
        assertEquals(3, messages?.size)
    }

    Test fun testLoadMessage() {
        val message = createMimeMessage("content");
        InboxFolder.add(message)

        val folder = connectToFolder()
        val receivedMessage = folder.getMessages()!![0]

        assertMailEquals(message, receivedMessage)
    }


    Test(expected = javaClass<AuthenticationFailedException>()) fun testWrongLogin() {
        config.authLogin = "wrong@username"
        connectToFolder()
    }

    Test(expected = javaClass<AuthenticationFailedException>()) fun testWrongPassword() {
        config.authPassword = "wrongPassword"
        connectToFolder()
    }

    fun connectToFolder(): Folder {
        val session = Session.getInstance(Properties());
        val store = session!!.getStore("pop3");
        store!!.connect("127.0.0.1", config.port, login, password);
        val folder = store.getFolder("inbox");
        folder!!.open(Folder.READ_ONLY);
        return folder
    }

    After fun tearDown() {
        server.stop()
    }
}
