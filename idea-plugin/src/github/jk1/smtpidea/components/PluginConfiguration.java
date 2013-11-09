package github.jk1.smtpidea.components;

import github.jk1.smtpidea.server.ServerConfiguration;

/**
 *
 */
public class PluginConfiguration {

    public final boolean launchOnStartup;
    public final ServerConfiguration smtpConfig;

    protected PluginConfiguration(boolean launchOnStartup, ServerConfiguration smtpConfig) {
        this.launchOnStartup = launchOnStartup;
        this.smtpConfig = smtpConfig;
    }
}
