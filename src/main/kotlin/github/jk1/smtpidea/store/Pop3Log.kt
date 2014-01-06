package github.jk1.smtpidea.log

import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultMutableTreeNode
import github.jk1.smtpidea.ui.TreeNodeWithIcon
import github.jk1.smtpidea.Icons
import java.util.Enumeration
import javax.swing.SwingUtilities

/**
 *
 */
public object Pop3Log : ServerLog, DefaultTreeModel(DefaultMutableTreeNode()) {

    val treeRoot = this.getRoot() as DefaultMutableTreeNode

    override fun logRequest(sessionId: String, content: String?) {
        SwingUtilities.invokeLater {
            val parent = getSessionNode(sessionId);
            this.insertNodeInto(TreeNodeWithIcon(content, Icons.CLIENT_REQUEST), parent, parent.getChildCount());
        }
    }
    override fun logResponse(sessionId: String, content: String?) {
        SwingUtilities.invokeLater {
            val parent = getSessionNode(sessionId);
            this.insertNodeInto(TreeNodeWithIcon(content, Icons.SERVER_RESPONSE), parent, parent.getChildCount());
        }
    }

    override fun clear() {
        (treeRoot.children() as Enumeration<DefaultMutableTreeNode>)
                .iterator()
                .forEach { this.removeNodeFromParent(it) }
    }

    private fun getSessionNode(sessionId: String): DefaultMutableTreeNode {
        //check if we already have a node for this session
        for (i in 0..treeRoot.getChildCount() - 1) {
            val sessionNode = treeRoot.getChildAt(i) as DefaultMutableTreeNode
            if (sessionId == sessionNode.getUserObject()) return sessionNode
        }
        // this must be a new session, let's create a node for it
        val sessionNode = TreeNodeWithIcon(sessionId, Icons.SESSION)
        this.insertNodeInto(sessionNode, treeRoot, treeRoot.getChildCount());
        return sessionNode
    }
}