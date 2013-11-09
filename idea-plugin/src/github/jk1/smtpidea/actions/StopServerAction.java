package github.jk1.smtpidea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.util.IconLoader;
import github.jk1.smtpidea.server.ConfigurableSmtpServer;

/**
 *
 */
public class StopServerAction extends AnAction {

    public StopServerAction() {
        super("Stop mail server", "Description", IconLoader.getIcon("/process/stop.png"));
    }

    public void actionPerformed(AnActionEvent e) {
        ConfigurableSmtpServer server = ServiceManager.getService(e.getProject(), ConfigurableSmtpServer.class);
        server.stopServer();
    }
}
