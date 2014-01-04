package github.jk1.smtpidea.log

import github.jk1.smtpidea.store.Clearable

/**
 *
 */
public trait ServerLog : Clearable {

    fun logRequest(sessionId: String, content: String?)

    fun logResponse(sessionId: String, content: String?)
}