package github.jk1.smtpidea.server

import javax.mail.internet.MimeMessage
import java.io.OutputStream
import java.util.Enumeration
import javax.mail.Header

/**
 * Contains ma
 *
 * @author Evgeny Naumenko
 */
fun MimeMessage.writeTo(out: OutputStream, lines: Int) {

    //this.getAllHeaderLines().
    val lol: Enumeration<MimeMessage>? = null;
    (this.getAllHeaderLines() as Enumeration<Header>).iterator()

}