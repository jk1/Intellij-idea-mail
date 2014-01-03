package github.jk1.smtpidea.server

/**
 * Abstract mail protocol session, represent a series of request-response
 * pairs. Session lifecycle is expected to match the client's socket
 * lifecycle. If client had disconnected or closed the session explicitly,
 * then session cannot be reused.
 */
public trait MailSession : Runnable {

    /**
     * Writes one line of server response to the client. This line is
     * guaranteed to be flushed immediately. Different application protocols
     * may have different opinion on how many lines should be in server response
     *
     * @param response content to be written, without any newline symbols
     */
    public fun writeResponseLine(response: String = "")

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
