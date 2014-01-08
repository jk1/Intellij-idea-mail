package github.jk1.smtpidea.ui

import javax.swing.event.TreeModelListener
import javax.swing.event.TreeModelEvent

/**
 * Simple adapter for TreeModelListener interface with empty method implementations.
 * Thanks to this adapter actual listener implementations shouldn't bother to
 * implement all the methods of TreeModelListener if they don't need to.
 */
trait TreeModelListenerAdapter : TreeModelListener {

    override fun treeNodesInserted(e: TreeModelEvent?) {}
    override fun treeNodesChanged(e: TreeModelEvent?) {}
    override fun treeNodesRemoved(e: TreeModelEvent?) {}
    override fun treeStructureChanged(e: TreeModelEvent?) {}
}