package github.jk1.smtpidea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import github.jk1.smtpidea.components.PluginConfiguration;
import github.jk1.smtpidea.server.ServerConfiguration;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 */
public class ConfigurationDialog extends DialogWrapper {

    private JSpinner portSpinner;
    private JCheckBox launchOnStartup;
    private OkActionCallback callback;

    public ConfigurationDialog(Project project, OkActionCallback callback) {
        super(project, false);
        this.callback = callback;
        this.init();
        this.setTitle("SMTP Server Configuration");
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        portSpinner = new JSpinner();
        portSpinner.setValue(25);
        panel.add(new JLabel("Port: "));
        panel.add(portSpinner);
        launchOnStartup = new JCheckBox("Launch on startup");
        launchOnStartup.setSelected(false);
        panel.add(launchOnStartup);
        return panel;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        return super.doValidate();
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        ServerConfiguration serverConfiguration = new ServerConfiguration((Integer) portSpinner.getValue());
        PluginConfiguration configuration = new PluginConfiguration(launchOnStartup.isSelected(), serverConfiguration);
        callback.configurationUpdated(configuration);
    }

    /**
     * Client code should use a callback to get updated configuration
     * when dialog is closed
     */
    public static interface OkActionCallback{

        void configurationUpdated(PluginConfiguration configuration);
    }
}
