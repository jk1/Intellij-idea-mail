package github.jk1.smtpidea.components;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import github.jk1.smtpidea.server.ConfigurableSmtpServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
@State(
        name = "SmtpConfiguration",
        storages = {
                @Storage(id = "smtp_plugin.config", file = StoragePathMacros.PROJECT_CONFIG_DIR + "smtp.config")
        }
)
public class SmtpServerComponent
        extends AbstractProjectComponent implements PersistentStateComponent<PluginConfiguration> {

    private PluginConfiguration configuration = PluginConfiguration.getDefault();
    private ConfigurableSmtpServer server;

    public SmtpServerComponent(@NotNull Project project) {
        super(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disposeComponent() {
        if (server.isRunning()) {
            server.stopServer();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initComponent() {
        server = ServiceManager.getService(myProject, ConfigurableSmtpServer.class);
        server.setConfiguration(configuration.smtpConfig);
        if (configuration.launchOnStartup) {
            server.startServer();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public PluginConfiguration getState() {
        return configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadState(PluginConfiguration pluginConfiguration) {
        if (pluginConfiguration != null) {
            configuration = pluginConfiguration;
        }
    }
}
