package github.jk1.smtpidea.log

import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeModel
import javax.swing.tree.DefaultMutableTreeNode

/**
 *
 */
public object Pop3Log : ServerLog, DefaultTreeModel(DefaultMutableTreeNode()) {

    val treeRoot = this.getRoot() as DefaultMutableTreeNode

    override fun logRequest(sessionId: String, content: String?) {
        println(">> $content")
        val parent = getSessionNode(sessionId);
        this.insertNodeInto(DefaultMutableTreeNode(">> $content"), parent, parent.getChildCount());
    }
    override fun logResponse(sessionId: String, content: String?) {
        println("<< $content")
        val parent = getSessionNode(sessionId);
        this.insertNodeInto(DefaultMutableTreeNode("<< $content"), parent, parent.getChildCount());
    }

    override fun clear() {
        println("==== Log cleared ====")
    }

    private fun getSessionNode(sessionId: String): DefaultMutableTreeNode {
        //check if we already have a node for this session
        for (i in 0..treeRoot.getChildCount() - 1) {
            val sessionNode = treeRoot.getChildAt(i) as DefaultMutableTreeNode
            if (sessionId == sessionNode.getUserObject()) return sessionNode
        }
        // this must be a new session, let's create a node for it
        val sessionNode = DefaultMutableTreeNode(sessionId)
        treeRoot.add(sessionNode)
        return sessionNode
    }
}