package github.jk1.smtpidea.server

/**
 * Represents any service, that can be run and stopped at any moment.
 * It exists mostly as a workaround for http://youtrack.jetbrains.com/issue/KT-4145 to avoid
 * covariant overrides when creating actions
 */
public trait RunnableService {

    public var running: Boolean

    fun start()

    fun stop()

}