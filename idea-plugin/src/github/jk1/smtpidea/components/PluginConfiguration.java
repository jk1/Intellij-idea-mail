package github.jk1.smtpidea.components;

import github.jk1.smtpidea.server.ServerConfiguration;

/**
 *
 */
public class PluginConfiguration {

    public final boolean launchOnStartup;
    public final ServerConfiguration smtpConfig;

    public PluginConfiguration(boolean launchOnStartup, ServerConfiguration smtpConfig) {
        this.launchOnStartup = launchOnStartup;
        this.smtpConfig = smtpConfig;
    }

    public static PluginConfiguration getDefault() {
        return new PluginConfiguration(false, new ServerConfiguration(25));
    }
}
