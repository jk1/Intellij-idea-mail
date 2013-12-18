package github.jk1.smtpidea.server

/**
 *
 * @author Evgeny Naumenko
 */
public trait MailSession : Runnable {

    /**
     * Clears the internal state of a session (authentication, etc)
     */
    public fun reset()

    /**
     * Closes the session. After the call any other commands
     * may not be possible to execute
     */
    public fun quit()
}
