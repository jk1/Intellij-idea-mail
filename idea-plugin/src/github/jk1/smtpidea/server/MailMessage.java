package github.jk1.smtpidea.server;

import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.james.mime4j.stream.BodyDescriptor;
import org.apache.james.mime4j.stream.Field;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents parsed MIME message, see RFCs 2045-2049 for more information on
 * it's structure. Typical e-mail is a MIME message, so we're going to threat
 * it as such.
 *
 * @author Evgeny Naumenko
 */
public class MailMessage implements ContentHandler {
    @Override
    public void startMessage() throws MimeException {
    }

    @Override
    public void endMessage() throws MimeException {
    }

    @Override
    public void startBodyPart() throws MimeException {
    }

    @Override
    public void endBodyPart() throws MimeException {
    }

    @Override
    public void startHeader() throws MimeException {
    }

    @Override
    public void field(Field field) throws MimeException {
    }

    @Override
    public void endHeader() throws MimeException {
    }

    @Override
    public void preamble(InputStream stream) throws MimeException, IOException {
    }

    @Override
    public void epilogue(InputStream stream) throws MimeException, IOException {
    }

    @Override
    public void startMultipart(BodyDescriptor bodyDescriptor) throws MimeException {
    }

    @Override
    public void endMultipart() throws MimeException {
    }

    @Override
    public void body(BodyDescriptor bodyDescriptor, InputStream stream) throws MimeException, IOException {
    }

    @Override
    public void raw(InputStream stream) throws MimeException, IOException {
    }
}
