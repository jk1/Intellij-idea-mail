package github.jk1.smtpidea.store

/**
 * Base abstraction for a storage, that can be cleared: log collectors, mailboxes, etc.
 */
public trait Clearable {

    fun clear()

}