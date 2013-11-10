package github.jk1.smtpidea.components;

import github.jk1.smtpidea.server.ServerConfiguration;

/**
 * Represents entire plugin configuration, project-scoped,
 * persistent between project close/reopen.
 *
 * @author Evgeny Naumenko
 */
public class PluginConfiguration {

    public final boolean launchOnStartup;
    public final ServerConfiguration smtpConfig;

    /**
     * @param launchOnStartup if SMTP server should be automatically launched on project load
     * @param smtpConfig mail server configuration
     */
    public PluginConfiguration(boolean launchOnStartup, ServerConfiguration smtpConfig) {
        this.launchOnStartup = launchOnStartup;
        this.smtpConfig = smtpConfig;
    }

    /**
     * @return default plugin configuration, plain server on port 25
     */
    public static PluginConfiguration getDefault() {
        return new PluginConfiguration(false, new ServerConfiguration(25));
    }
}
