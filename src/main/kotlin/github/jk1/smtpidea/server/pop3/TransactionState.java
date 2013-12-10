package github.jk1.smtpidea.server.pop3;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import javax.mail.Session;

import github.jk1.smtpidea.server.base.Message;
import github.jk1.smtpidea.server.base.MessageFolder;
import github.jk1.smtpidea.server.base.MessageResource;
import github.jk1.smtpidea.server.util.ChunkWriter;
import github.jk1.smtpidea.server.util.ChunkingOutputStream;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;


/**
 * @author Evgeny Naumenko
 */
public class TransactionState extends BaseState {

    private MessageFolder inbox;

    TransactionState(Pop3Session popSession) {
        super(popSession);
        this.popSession = popSession;
        popSession.setMessages(new ArrayList<Message>());
        inbox = popSession.getAuth().getMbox().getInbox();
        if( inbox != null ) {
            int num = 1;
            Collection<MessageResource> messageResources = inbox.getMessages();
            if( messageResources != null ) {
                for (MessageResource mr : messageResources) {
                    Message m = new Message(mr, num++);
                    popSession.getMessages().add(m);
                }
            }
        }
    }

    private Message get(Pop3Session popSession, int num) {
        return popSession.getMessages().get(num - 1);
    }

    public void enter(IoSession session, Pop3Session popSession) {
        // don't know what this is doing here..
//        popSession.reply(session, "+OK " + popSession.auth.user + " has " + inbox.numMessages() + " messages (" + inbox.totalSize() + " octets)");
    }

    public void exit(IoSession session, Pop3Session popSession) {
    }

    public void uidl(IoSession session, Pop3Session popSession, String[] args) {
        if (args.length <= 1) {
            popSession.reply(session, "+OK");
            for (Message m : popSession.getMessages()) {
                popSession.reply(session, "" + m.getId() + " " + m.hashCode());
            }
            popSession.reply(session, ".");
        } else {
            String sNum = args[1];
            int num = Integer.parseInt(sNum);
            Message m = get(popSession, num);
            if (m == null) {
                popSession.reply(session, "-ERR no such message");
            } else {
                popSession.reply(session, "+OK " + m.hashCode());
            }
        }
    }

    public void list(IoSession session, Pop3Session popSession, String[] args) {
        if (args.length <= 1) {
            popSession.reply(session, "+OK");
            for (Message m : popSession.getMessages()) {
                popSession.reply(session, "" + m.getId() + " " + m.size());
            }
            popSession.reply(session, ".");
        } else {
            String sNum = args[1];
            int num = Integer.parseInt(sNum);
            Message m = get(popSession, num);
            if (m == null) {
                popSession.reply(session, "-ERR no such message");
            } else {
                popSession.reply(session, "+OK " + m.size());
            }
        }
    }

    public void capa(IoSession session, Pop3Session popSession, String[] args) {
        popSession.reply(session, "+OK Capability list follows");
        popSession.reply(session, ".");
    }

    public void stat(IoSession session, Pop3Session popSession, String[] args) {
        int size = 0;
        if( inbox != null ) {
            size = inbox.totalSize();
        }
        int messages = 0;
        if(popSession.getMessages() != null) {
            messages = popSession.getMessages().size();
        }
        popSession.reply(session, "+OK " + messages + " " + size);
    }

    public void retr(final IoSession session, Pop3Session popSession, String[] args) {
        String sNum = args[1];
        int num = Integer.parseInt(sNum);
        Message m = get(popSession, num);

        if (m == null) {
            popSession.reply(session, "-ERR no such message");
        } else {
            popSession.reply(session, "+OK " + m.size() + " octets");
            Session mailSess = null;
            ChunkWriter store = new ChunkWriter() {
                public void newChunk(int i, byte[] data) {
                    IoBuffer bb = IoBuffer.wrap(data);
                    session.write(bb);
                }
            };
            ChunkingOutputStream out = new ChunkingOutputStream(store, 1024);
            try {
                m.getResource().writeTo(out);
                out.flush();
            } catch (Exception e) {
                // log?
            } finally {
                close(out);
            }
            popSession.reply(session, ".");

        }
    }

    private void close(OutputStream out) {
        if( out == null ) return ;
        try {
            out.close();
        } catch (IOException ex) {
            // log?
        }
    }

    public void dele(IoSession session, Pop3Session popSession, String[] args) {
        String sNum = args[1];
        int num = Integer.parseInt(sNum);
        Message mid = get(popSession, num);
        if (mid != null) {
            mid.markForDeletion();
            popSession.reply(session, "+OK");
            return;
        } else {
            popSession.reply(session, "-ERR no such message");
        }
    }
}