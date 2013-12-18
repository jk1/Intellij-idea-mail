package github.jk1.smtpidea.server.pop3.command

import github.jk1.smtpidea.server.pop3.Pop3Session

/**
 *
 */
 object RsetCommand : Pop3Command("RSET"){

     override fun execute(arguments: List<String>, session: Pop3Session) {
         session.reset();
         session.writeOkResponseLine("Session is reset")
     }
 }