package github.jk1.smtpidea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

/**
 *
 */
public class MailToolWindowFactory implements ToolWindowFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ContentFactory factory = ContentFactory.SERVICE.getInstance();
        Content content = factory.createContent(new MailToolPanel(), "Mail", true);
        toolWindow.getContentManager().addContent(content);
    }
}
