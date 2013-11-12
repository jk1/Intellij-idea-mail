package github.jk1.smtpidea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import github.jk1.smtpidea.components.PluginConfiguration;
import github.jk1.smtpidea.components.SmtpServerComponent;
import github.jk1.smtpidea.server.ServerConfiguration;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static github.jk1.smtpidea.server.ServerConfiguration.AuthType;
import static github.jk1.smtpidea.server.ServerConfiguration.AuthType.*;
import static github.jk1.smtpidea.server.ServerConfiguration.TransportSecurity;
import static github.jk1.smtpidea.server.ServerConfiguration.TransportSecurity.*;

/**
 * Plugin configuration dialog
 *
 * @author Evgeny Naumenko
 */
public class ConfigurationDialog extends DialogWrapper {

    private SmtpServerComponent component;

    private JSpinner portSpinner = new JSpinner();
    private JCheckBox launchOnStartup = new JCheckBox("Launch on startup");

    private EnumRadioGroup<AuthType> authRadioGroup = new EnumRadioGroup<AuthType>();
    private JRadioButton noAuthenticationRadio = new JRadioButton("Anonymous mode");
    private JRadioButton authSupportedRadio = new JRadioButton("Support optional authentication");
    private JRadioButton authEnforcedRadio = new JRadioButton("Require authentication");

    private EnumRadioGroup<TransportSecurity> sslRadioGroup = new EnumRadioGroup<TransportSecurity>();
    private JRadioButton plainRadio = new JRadioButton("Plain SMTP");
    private JRadioButton startTlsSupportedRadio = new JRadioButton("Support STARTTLS");
    private JRadioButton startTlsEnforcedRadio = new JRadioButton("Require STARTTLS");
    private JRadioButton sslOnConnectRadio = new JRadioButton("SSL/TLS on connect (SMTPS)");

    private JTextField login = new JTextField(20);
    private JTextField password = new JTextField(20);

    /**
     * @param project current project (plugin configuration is project-scoped)
     */
    public ConfigurationDialog(Project project) {
        super(project, false);
        component = project.getComponent(SmtpServerComponent.class);
        this.setTitle("SMTP Server Configuration");
        this.init();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel contextPane = new JPanel(new BorderLayout());
        contextPane.add(createTopPanel(), BorderLayout.NORTH);
        contextPane.add(createAuthPane(), BorderLayout.WEST);
        contextPane.add(createSslPane(), BorderLayout.EAST);
        this.installCurrentConfigurationValues(component.getState());
        return contextPane;
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        Border lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "General"));
        panel.add(new JLabel("Port: "));
        panel.add(portSpinner);
        panel.add(launchOnStartup);
        return panel;
    }

    private JPanel createAuthPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        Border lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Authentication"));
        authRadioGroup.add(noAuthenticationRadio, DISABLED);
        authRadioGroup.add(authSupportedRadio, SUPPORTED);
        authRadioGroup.add(authEnforcedRadio, ENFORCED);
        panel.add(noAuthenticationRadio);
        ActionListener listener = new AuthenticationSetupActionListener();
        noAuthenticationRadio.addActionListener(listener);
        authSupportedRadio.addActionListener(listener);
        authEnforcedRadio.addActionListener(listener);
        panel.add(authSupportedRadio);
        panel.add(authEnforcedRadio);
        panel.add(wrap("Username: ", login));
        panel.add(wrap("Password: ", password));
        return panel;
    }

    private JPanel createSslPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        Border lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "SSL/TLS"));
        sslRadioGroup.add(plainRadio, PLAINTEXT);
        sslRadioGroup.add(startTlsSupportedRadio, STARTTLS_SUPPORTED);
        sslRadioGroup.add(startTlsEnforcedRadio, STARTTLS_ENFORCED);
        sslRadioGroup.add(sslOnConnectRadio, SSL);
        panel.add(plainRadio);
        panel.add(startTlsSupportedRadio);
        panel.add(startTlsEnforcedRadio);
        panel.add(sslOnConnectRadio);
        return panel;
    }

    private JPanel wrap(String labelText, JComponent jComponent) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(labelText));
        panel.add(jComponent);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    /**
     * Installs current configuration values in UI controls
     *
     * @param configuration current plugin configuration
     */
    private void installCurrentConfigurationValues(PluginConfiguration configuration) {
        portSpinner.setValue(configuration.smtpConfig.port);
        launchOnStartup.setSelected(configuration.launchOnStartup);
        authRadioGroup.setSelected(configuration.smtpConfig.authType);
        sslRadioGroup.setSelected(configuration.smtpConfig.transportSecurity);
        login.setText(configuration.smtpConfig.login);
        password.setText(configuration.smtpConfig.password);
    }

    /**
     *  Saves selected configuration and closes configuration dialog
     */
    @Override
    protected void doOKAction() {
        super.doOKAction();
        ServerConfiguration serverConfiguration = new ServerConfiguration( );
        serverConfiguration.port = (Integer) portSpinner.getValue();
        serverConfiguration.authType = authRadioGroup.getSelected();
        serverConfiguration.transportSecurity = sslRadioGroup.getSelected();
        serverConfiguration.login = login.getText();
        serverConfiguration.password = password.getText();
        PluginConfiguration configuration = new PluginConfiguration();
        configuration.launchOnStartup = launchOnStartup.isSelected();
        configuration.smtpConfig = serverConfiguration;
        component.loadState(configuration);
    }

    /**
     * Disables login/password text fields if authentication is turned off
     */
    private class AuthenticationSetupActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            login.setEnabled(!noAuthenticationRadio.isSelected());
            password.setEnabled(!noAuthenticationRadio.isSelected());
        }
    }
}
