package github.jk1.smtpidea.server;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MailStore extends DefaultTableModel {

    private static List<MailSessionInfo> messages = new ArrayList<MailSessionInfo>();

    public static void addMessage(MailSessionInfo info) {
        messages.add(info);
    }

    @Override
    public int getRowCount() {
        return messages.size();
    }

    @Override
    public int getColumnCount() {
        return MailSessionInfo.getFieldCount();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "Column";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
