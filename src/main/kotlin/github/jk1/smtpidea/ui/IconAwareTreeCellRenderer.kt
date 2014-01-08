package github.jk1.smtpidea.ui

import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.JTree
import java.awt.Component
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.JLabel
import javax.swing.SwingConstants

/**
 *  Replaces default icons for JTree node with custom ones, if node itself is able to provide an icon
 */
object IconAwareTreeCellRenderer : DefaultTreeCellRenderer() {

    override fun getTreeCellRendererComponent(tree: JTree, value: Any?, sel: Boolean, expanded: Boolean,
                                              leaf: Boolean, row: Int, hasFocus: Boolean): Component {
        return when (value) {
            is TreeNodeWithIcon -> JLabel(value.getUserObject().toString(), value.icon, SwingConstants.LEADING)
            else -> super<DefaultTreeCellRenderer>.getTreeCellRendererComponent(
                    tree, value, sel, expanded, leaf, row, hasFocus)
        }
    }
}