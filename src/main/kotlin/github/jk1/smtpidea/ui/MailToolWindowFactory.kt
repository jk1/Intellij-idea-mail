package github.jk1.smtpidea.ui

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;

/**
 *
 */
public class MailToolWindowFactory : ToolWindowFactory {

    public override fun createToolWindowContent(project: Project?, toolWindow: ToolWindow?) {
        fun add(content : BaseContent){
            toolWindow?.getContentManager()?.addContent(content.asContent())
        }
        add(SmtpServerContent(project!!))
        add(SmtpLogContent())
        add(Pop3ServerContent())
        add(Pop3LogContent())
    }
}
