package github.jk1.smtpidea

import com.intellij.openapi.util.IconLoader

/**
 *  Holder object for all plugin icons
 */
public object Icons{
    val ADD = IconLoader.getIcon("/add.png")
    val SETTINGS = IconLoader.getIcon("/settings.png")
    val CLEAR = IconLoader.getIcon("/clear.png")
    val START = IconLoader.getIcon("/run.png")
    val STOP = IconLoader.getIcon("/cancel.png")
    val EXPAND = IconLoader.getIcon("/expandall.png")
    val COLLAPSE = IconLoader.getIcon("/collapseall.png")

    val CLIENT_REQUEST = IconLoader.getIcon("/right.png")
    val SERVER_RESPONSE = IconLoader.getIcon("/left.png")
    val SESSION = IconLoader.getIcon("/session.png")
}