import javax.mail.internet.MimeMessage

/**
 * @author Evgeny Naumenko
 */
public trait MessageFolder {
    /**
     *
     * @return - collection of all messages in this folder
     */
    open fun getMessages() : List<MimeMessage>
    /**
     *
     * @return -  number of messages in this folder
     */
    open fun numMessages() : Int
    /**
     *
     * @return - sum of octet size of all messages in this folder
     */
    open fun totalSize() : Int

}
