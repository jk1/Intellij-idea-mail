package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session
import github.jk1.smtpidea.server.pop3.CommandHandler


/**
 *
 * @author Evgeny Naumenko
 */
public abstract class Pop3Command(public val name: String, val wrap : Boolean = false){

    {CommandHandler + (if (wrap) AuthWrapper(this) else this)}

    public abstract fun execute(arguments: List<String>, session: Pop3Session)
}