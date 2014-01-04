package github.jk1.smtpidea.log

/**
 *
 */
public trait ServerLog {

    fun logRequest(sessionId: String, content: String?)

    fun logResponse(sessionId: String, content: String?)
}