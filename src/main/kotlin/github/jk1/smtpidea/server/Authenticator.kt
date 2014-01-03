package github.jk1.smtpidea.server

import org.subethamail.smtp.auth.UsernamePasswordValidator
import org.subethamail.smtp.auth.LoginFailedException
import java.security.MessageDigest

/**
 *
 */
public class Authenticator(val username: String, val password : String) : UsernamePasswordValidator{

    /**
     * Performs authentication with a simple login-password pair
     */
    public override fun login(username: String?, password: String?) {
        if (!this.username.equals(username) || !this.password.equals(password)) {
            throw LoginFailedException()
        }
    }

    /**
     * Performs authentication with a plain login and hashed password.
     * Password is concatenated with one time session id before hashing,
     * so this implementation verifies a hash based on session id and a
     * known password. Used for POP3 APOP implementation mostly
     */
    public fun login(login: String?, sessionId : String, passwordHash: ByteArray?) {
        val md5 = MessageDigest.getInstance("MD5").digest((sessionId + password).getBytes())
        if (!this.username.equals(login) || !md5.equals(passwordHash)) {
            throw LoginFailedException()
        }
    }
}

