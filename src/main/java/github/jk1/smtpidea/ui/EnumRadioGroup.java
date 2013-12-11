package github.jk1.smtpidea.ui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Radio button group mapped to enum set. Simplifies
 * UI control bindings for enum-typed configuration properties.
 *
 * @author Evgeny Naumenko
 */
public class EnumRadioGroup<T extends Enum> extends ButtonGroup {

    private Map<T, AbstractButton> mapping = new HashMap<T, AbstractButton>();

    public void add(AbstractButton b, T value) {
        super.add(b);
        mapping.put(value, b);

    }

    public T getSelected() {
        for (Map.Entry<T, AbstractButton> entry : mapping.entrySet()) {
            if (entry.getValue().isSelected()) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException("No selected value found for EnumRadioGroup");
    }

    public void setSelected(T value) {
        mapping.get(value).setSelected(true);
    }
}
