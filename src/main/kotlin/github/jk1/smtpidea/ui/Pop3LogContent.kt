package github.jk1.smtpidea.ui

import github.jk1.smtpidea.log.Pop3Log
import javax.swing.JTree
import org.apache.log4j.lf5.viewer.categoryexplorer.TreeModelAdapter
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import github.jk1.smtpidea.actions.ActionRegistry
import javax.swing.event.TreeModelEvent

/**
 *
 */
class Pop3LogContent : BaseContent("POP3 Log") {
    {
        val logTree = JTree(Pop3Log);
        logTree.setRootVisible(false);
        Pop3Log.addTreeModelListener(object : TreeModelAdapter(){
            override fun treeNodesInserted(e: TreeModelEvent?) {
                logTree.expandPath(e?.getTreePath());
            }
        })
        add(JBScrollPane(logTree), BorderLayout.CENTER);
        add(createActionsButtonPane(ActionRegistry.CLEAR_POP3_LOG), BorderLayout.WEST);
    }
}