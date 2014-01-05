package github.jk1.smtpidea.ui

import github.jk1.smtpidea.log.Pop3Log
import javax.swing.JTree
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import github.jk1.smtpidea.actions.ActionRegistry
import javax.swing.event.TreeModelEvent
import javax.swing.event.TreeModelListener

/**
 *
 */
class Pop3LogContent : BaseContent("POP3 Log") {

    val logTree = JTree(Pop3Log);

    val pop3LogListener = object : TreeModelListener{

        override fun treeNodesInserted(e: TreeModelEvent?) {
            logTree.expandPath(e?.getTreePath());
        }

        override fun treeNodesChanged(e: TreeModelEvent?) {}
        override fun treeNodesRemoved(e: TreeModelEvent?) {}
        override fun treeStructureChanged(e: TreeModelEvent?) {}
    }

    {
        logTree.setRootVisible(false);
        Pop3Log.addTreeModelListener(pop3LogListener)
        add(JBScrollPane(logTree), BorderLayout.CENTER);
        add(createActionsButtonPane(ActionRegistry.CLEAR_POP3_LOG), BorderLayout.WEST);
    }


}