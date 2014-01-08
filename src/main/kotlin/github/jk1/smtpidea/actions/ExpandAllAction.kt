package github.jk1.smtpidea.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import github.jk1.smtpidea.Icons
import javax.swing.JTree

/**
 *
 */
class ExpandAllAction(val tree : JTree) : AnAction("Expand all", "Expand all sessions in a log tree", Icons.EXPAND) {

    override fun actionPerformed(action: AnActionEvent?) {
        throw UnsupportedOperationException()
    }
}