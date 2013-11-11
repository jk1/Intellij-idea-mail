package github.jk1.smtpidea.components;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import github.jk1.smtpidea.server.SmtpServerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Evgeny Naumenko
 */
@State(
        name = "SmtpServerComponent",
        storages = {
                @Storage(id = "default", file = StoragePathMacros.PROJECT_FILE),
                @Storage(id = "dir", file = StoragePathMacros.PROJECT_CONFIG_DIR + "/smtp.xml",
                         scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class SmtpServerComponent
        extends AbstractProjectComponent implements PersistentStateComponent<PluginConfiguration> {

    private PluginConfiguration configuration = new PluginConfiguration();
    private SmtpServerManager server;

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
        server = ServiceManager.getService(myProject, SmtpServerManager.class);
        server.setConfiguration(configuration.smtpConfig);
        if (configuration.launchOnStartup) {
            server.startServer();
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getComponentName() {
        return "SmtpServerComponent";
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
        //may be called any time, check everything
        if (pluginConfiguration != null) {
            configuration = pluginConfiguration;
            if (server != null) {
                server.setConfiguration(pluginConfiguration.smtpConfig);
            }
        }
    }
}
