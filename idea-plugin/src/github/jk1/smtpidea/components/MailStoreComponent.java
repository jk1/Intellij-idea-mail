package github.jk1.smtpidea.components;

import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import github.jk1.smtpidea.server.smtp.MailSession;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MailStoreComponent extends AbstractProjectComponent {

    private static List<MailSession> messages = new ArrayList<MailSession>();
    private MailTableModel model = new MailTableModel();

    public MailStoreComponent(@NotNull Project project) {
        super(project);
    }


    public void addMessage(final MailSession info) {
        // method may be called from outside EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messages.add(info);
                model.fireTableDataChanged();
            }
        });
    }

    public MailTableModel getTableModel() {
        return model;
    }

    public void clear() {
        // method may be called from outside EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messages.clear();
                model.fireTableDataChanged();
            }
        });
    }

    public class MailTableModel extends DefaultTableModel {

        private DateFormat format = new SimpleDateFormat("DD MMM HH:mm:ss");
        private String[] columns = {"Received", "IP", "From (Envelope)", "Recipients (Envelope)"};

        /**
         * {@inheritDoc}
         */
        @Override
        public int getRowCount() {
            return messages.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getColumnCount() {
            return 4;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getColumnName(int columnIndex) {
            return columns[columnIndex];
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            MailSession info = messages.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return format.format(info.getReceivedDate());
                case 1:
                    return info.getIp();
                case 2:
                    return info.getEnvelopeFrom();
                case 3:
                    return info.getEnvelopeRecipients();
            }
            throw new IllegalStateException("No value defined for column " + columnIndex);
        }

        public MailSession getMailSessionInfo(int index) {
            return messages.get(index);
        }
    }
}
