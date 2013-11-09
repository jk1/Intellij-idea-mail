package github.jk1.smtpidea.server;


import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.TooMuchDataException;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class IncomingMessageHandler implements MessageHandler {


    @Override
    public void from(String from) throws RejectException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void recipient(String recipient) throws RejectException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void data(InputStream data) throws RejectException, TooMuchDataException, IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void done() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
