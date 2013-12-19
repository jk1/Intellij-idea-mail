package github.jk1.smtpidea.server

import org.subethamail.smtp.auth.UsernamePasswordValidator
import org.subethamail.smtp.auth.LoginFailedException
import java.security.MessageDigest

/**
 *
 * @author Evgeny Naumenko
 */
public class Authenticator(val login : String, val password : String) : UsernamePasswordValidator{

    /**
     * Performs authentication with a simple login-password pair
     */
    public override fun login(login: String?, password: String?) {
        if (this.login.equals(login) || this.password.equals(password)) {
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
        if (this.login.equals(login) || md5.equals(passwordHash)) {
            throw LoginFailedException()
        }
    }
}

