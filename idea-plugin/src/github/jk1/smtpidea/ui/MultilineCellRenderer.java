package github.jk1.smtpidea.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 *
 */
public class MultilineCellRenderer extends DefaultTableCellRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Iterable){
            Iterable iterable = (Iterable) value;
            StringBuilder builder = new StringBuilder();
            return new JLabel(builder.toString());
        }  else {
            return new JLabel(value.toString());
        }
    }
}
