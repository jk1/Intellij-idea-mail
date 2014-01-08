package github.jk1.smtpidea.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.project.Project
import javax.swing.JComponent
import javax.swing.JPanel

/**
 *
 */
public class NewMailDialog(project: Project?) : DialogWrapper(project) {

    {
        setTitle("Create new message")
        init()
    }

    override fun createCenterPanel(): JComponent {
        return JPanel()
    }

}