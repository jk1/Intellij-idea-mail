package github.jk1.smtpidea.server;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MailStore extends DefaultTableModel {

    private static List<MailSessionInfo> messages = new ArrayList<MailSessionInfo>();

    public static final MailStore INSTANCE = new MailStore();

    public static void addMessage(MailSessionInfo info) {
        messages.add(info);
    }

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
        return MailSessionInfo.getFieldCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName(int columnIndex) {
        return "Column";
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
        return messages.get(rowIndex).getValue(columnIndex);
    }
}
