package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import javax.swing.JTree

/**
 *
 */
class CollapseAllAction(val tree : JTree) : AnAction("Collapse all", "Collapse all sessions in a log tree", Icons.COLLAPSE) {

    override fun actionPerformed(action: AnActionEvent?) {
        throw UnsupportedOperationException()
    }
}