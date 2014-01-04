package github.jk1.smtpidea.ui

import java.awt.BorderLayout
import javax.swing.JPanel
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.DefaultActionGroup
import javax.swing.JComponent
import com.intellij.ui.content.ContentFactory

/**
 *  Base class for plugin tool window tabs (contents)
 */
open class BaseContent(val name: String) : JPanel(BorderLayout()){

    private val factory: ContentFactory = ContentFactory.SERVICE.getInstance()!!

    fun createActionsButtonPane(vararg actions: AnAction): JComponent {
        val group = DefaultActionGroup()
        actions.forEach { group.add(it) }
        return ActionManager.getInstance()!!
                .createActionToolbar("Actions", group, false)!!
                .getComponent()!!
    }

    fun asContent() = factory.createContent(this, name, true)
}
