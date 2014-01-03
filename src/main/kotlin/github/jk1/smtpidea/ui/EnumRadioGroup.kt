package github.jk1.smtpidea.ui

import javax.swing.ButtonGroup
import javax.swing.AbstractButton
import java.util.HashMap

/**
 * Radio button group mapped to enum set. Simplifies
 * UI control bindings for enum-typed configuration properties.
 */
public class EnumRadioGroup<T> : ButtonGroup(){

    private var mapping: MutableMap<T, AbstractButton> = HashMap<T, AbstractButton>()

    public fun add(b: AbstractButton, value: T) {
        super.add(b)
        mapping.put(value, b)
    }

    public fun getSelected(): T {
        for (entry in mapping.entrySet()) {
            if (entry.getValue().isSelected()) {
                return entry.getKey()
            }
        }
        throw IllegalStateException("No selected value found for EnumRadioGroup")
    }

    public fun setSelected(value: T): Unit = mapping.get(value)?.setSelected(true)
}