package github.jk1.smtpidea.components;

import github.jk1.smtpidea.server.ServerConfiguration;

/**
 * Represents entire plugin configuration, project-scoped,
 * persistent between project close/reopen.
 *
 * @author Evgeny Naumenko
 */
public class PluginConfiguration {

    /**
     * If SMTP server should be automatically launched on project load
     */
    public boolean launchOnStartup = false;

    /**
     * Mail server configuration
     */
    public ServerConfiguration smtpConfig = new ServerConfiguration();
}
