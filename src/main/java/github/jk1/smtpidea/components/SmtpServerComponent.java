package github.jk1.smtpidea.components;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import github.jk1.smtpidea.config.SmtpConfig;
import github.jk1.smtpidea.server.smtp.SmtpServerManager;
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
public class SmtpServerComponent extends AbstractProjectComponent implements PersistentStateComponent<SmtpConfig> {

    private SmtpConfig configuration = new SmtpConfig();
    private SmtpServerManager server;

    public SmtpServerComponent(@NotNull Project project) {
        super(project);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disposeComponent() {
        if (server.getRunning()) {
            server.stopServer();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initComponent() {
        server = ServiceManager.getService(myProject, SmtpServerManager.class);
        server.setConfiguration(configuration);
        if (configuration.getLaunchOnStartup()) {
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
    public SmtpConfig getState() {
        return configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadState(SmtpConfig smtpConfig) {
        //may be called any time, check everything
        if (smtpConfig != null) {
            configuration = smtpConfig;
            if (server != null) {
                server.setConfiguration(smtpConfig);
            }
        }
    }
}
