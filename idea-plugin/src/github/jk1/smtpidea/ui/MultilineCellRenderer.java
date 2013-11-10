package github.jk1.smtpidea.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Renders collection in a JTable cell as multiline list.
 * This renderer should only be used with values implementing Iterable
 *
 * @author Evgeny Naumenko
 */
public class MultilineCellRenderer extends DefaultTableCellRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Iterable iterable = (Iterable) value;
        StringBuilder builder = new StringBuilder();
        for (Object item : iterable) {
            builder.append(item.toString()).append("\n");
        }
        return new JLabel(builder.toString());
    }
}
