package github.jk1.smtpidea.log

import github.jk1.smtpidea.store.Clearable
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultMutableTreeNode
import github.jk1.smtpidea.ui.TreeNodeWithIcon
import github.jk1.smtpidea.Icons
import java.util.Enumeration
import javax.swing.SwingUtilities

/**
 *  Accumulates all mail protocol commands and responses, grouped by corresponding protocol sessions.
 *  This implementation also offers all containing data as JTree model for UI log representation.
 *
 *  It is safe to operate this instance from any arbitrary thread.
 */
public abstract class ServerLog :  Clearable, DefaultTreeModel(DefaultMutableTreeNode()) {

    val treeRoot = this.getRoot() as DefaultMutableTreeNode

    public fun logRequest(sessionId: String, content: String?) {
        SwingUtilities.invokeLater {
            val parent = getSessionNode(sessionId);
            this.insertNodeInto(TreeNodeWithIcon(content, Icons.CLIENT_REQUEST), parent, parent.getChildCount());
        }
    }

    public fun logResponse(sessionId: String, content: String?) {
        SwingUtilities.invokeLater {
            val parent = getSessionNode(sessionId);
            this.insertNodeInto(TreeNodeWithIcon(content, Icons.SERVER_RESPONSE), parent, parent.getChildCount());
        }
    }

    override fun clear() {
        SwingUtilities.invokeLater {
            (treeRoot.children() as Enumeration<DefaultMutableTreeNode>)
                    .iterator()
                    .forEach { this.removeNodeFromParent(it) }
        }
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

public object Pop3Log: ServerLog()
public object SmtpLog: ServerLog()