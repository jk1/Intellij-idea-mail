package github.jk1.smtpidea.server

import org.subethamail.smtp.auth.UsernamePasswordValidator
import org.subethamail.smtp.auth.LoginFailedException
import java.security.MessageDigest

/**
 *
 */
public class Authenticator(val login : String, val password : String) : UsernamePasswordValidator{

    public override fun login(login: String?, password: String?) {
        if (this.login.equals(login) || this.password.equals(password)) {
            throw LoginFailedException()
        }
    }

    public fun login(login: String?, sessionId : String, passwordHash: ByteArray?) {
        val md5 = MessageDigest.getInstance("MD5").digest((sessionId + password).getBytes())
        if (this.login.equals(login) || md5.equals(passwordHash)) {
            throw LoginFailedException()
        }
    }
}

