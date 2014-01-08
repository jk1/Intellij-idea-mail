package github.jk1.smtpidea.ui

import javax.swing.JTree
import github.jk1.smtpidea.log.SmtpLog
import javax.swing.event.TreeModelEvent
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import github.jk1.smtpidea.actions.ActionRegistry

/**
 *
 */
class SmtpLogContent : BaseContent("SMTP Log") {
    val logTree = JTree(SmtpLog);

    val logListener = object : TreeModelListenerAdapter{
        override fun treeNodesInserted(e: TreeModelEvent?) {
            logTree.expandPath(e?.getTreePath());
        }
    }

    {
        logTree.setRootVisible(false)
        logTree.setCellRenderer(IconAwareTreeCellRenderer)
        SmtpLog.addTreeModelListener(logListener)
        add(JBScrollPane(logTree), BorderLayout.CENTER)
        add(createActionsButtonPane(
                ActionRegistry.expandAll(logTree),
                ActionRegistry.collapseAll(logTree),
                ActionRegistry.clearSmtpLog()), BorderLayout.WEST)
    }
}