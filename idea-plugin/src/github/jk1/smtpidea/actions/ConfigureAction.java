package github.jk1.smtpidea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.util.IconLoader;
import github.jk1.smtpidea.components.PluginConfiguration;
import github.jk1.smtpidea.server.ConfigurableSmtpServer;
import github.jk1.smtpidea.ui.ConfigurationDialog;

/**
 *
 *
 * @author Evgeny Naumenko
 */
public class ConfigureAction extends AnAction {

    public ConfigureAction() {
        super("Configure", "Description", IconLoader.getIcon("/general/settings.png"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void actionPerformed(final AnActionEvent e) {
        final ConfigurableSmtpServer server = ServiceManager.getService(e.getProject(), ConfigurableSmtpServer.class);
        DialogWrapper dialog = new ConfigurationDialog(e.getProject(), new ConfigurationDialog.OkActionCallback() {
            @Override
            public void configurationUpdated(PluginConfiguration configuration) {
                server.setConfiguration(configuration.smtpConfig);
            }
        });
        dialog.show();
    }

}
