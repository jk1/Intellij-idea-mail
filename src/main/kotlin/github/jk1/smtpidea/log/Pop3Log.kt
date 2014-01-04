package github.jk1.smtpidea.log

/**
 *
 */
public object Pop3Log : ServerLog {

    override fun logRequest(sessionId: String, content: String?) {
        println(">> $content")
    }
    override fun logResponse(sessionId: String, content: String?) {
        println("<< $content")
    }
}