package github.jk1.smtpidea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import github.jk1.smtpidea.components.PluginConfiguration;
import github.jk1.smtpidea.components.SmtpServerComponent;
import github.jk1.smtpidea.server.ServerConfiguration;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 * @author Evgeny Naumenko
 */
public class ConfigurationDialog extends DialogWrapper {

    private SmtpServerComponent component;

    private JSpinner portSpinner;
    private JCheckBox launchOnStartup;

    public ConfigurationDialog(Project project) {
        super(project, false);
        this.component = project.getComponent(SmtpServerComponent.class);
        this.setTitle("SMTP Server Configuration");
        this.init();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        PluginConfiguration config = component.getState();
        JPanel panel = new JPanel();
        portSpinner = new JSpinner();
        portSpinner.setValue(config.smtpConfig.port);
        panel.add(new JLabel("Port: "));
        panel.add(portSpinner);
        launchOnStartup = new JCheckBox("Launch on startup");
        launchOnStartup.setSelected(config.launchOnStartup);
        panel.add(launchOnStartup);
        return panel;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        return super.doValidate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doOKAction() {
        super.doOKAction();
        ServerConfiguration serverConfiguration = new ServerConfiguration((Integer) portSpinner.getValue());
        PluginConfiguration configuration = new PluginConfiguration(launchOnStartup.isSelected(), serverConfiguration);
        component.loadState(configuration);
    }
}
