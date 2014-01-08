package github.jk1.smtpidea.ui

import github.jk1.smtpidea.log.Pop3Log
import javax.swing.JTree
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import javax.swing.event.TreeModelEvent
import github.jk1.smtpidea.actions.ActionRegistry

/**
 *
 */
class Pop3LogContent : BaseContent("POP3 Log") {

    val logTree = JTree(Pop3Log);

    val logListener = object : TreeModelListenerAdapter{
        override fun treeNodesInserted(e: TreeModelEvent?) {
            logTree.expandPath(e?.getTreePath());
        }
    }

    {
        logTree.setRootVisible(false)
        logTree.setCellRenderer(IconAwareTreeCellRenderer)
        Pop3Log.addTreeModelListener(logListener)
        add(JBScrollPane(logTree), BorderLayout.CENTER)
        add(createActionsButtonPane(
                ActionRegistry.expandAll(logTree),
                ActionRegistry.collapseAll(logTree),
                ActionRegistry.clearPop3Log()), BorderLayout.WEST)
    }
}