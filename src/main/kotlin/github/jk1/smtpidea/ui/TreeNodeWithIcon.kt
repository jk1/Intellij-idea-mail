package github.jk1.smtpidea.ui

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.Icon

/**
 * JTree node extension with a custom icon
 */
public class TreeNodeWithIcon(payload : String?, public val icon : Icon) : DefaultMutableTreeNode(payload){}
