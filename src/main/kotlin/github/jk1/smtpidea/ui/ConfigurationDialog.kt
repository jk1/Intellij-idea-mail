package github.jk1.smtpidea.ui

import javax.swing.JRadioButton
import javax.swing.JCheckBox
import javax.swing.JSpinner
import javax.swing.JTextField

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.project.Project

import github.jk1.smtpidea.config.SmtpConfig.TransportSecurity
import github.jk1.smtpidea.config.SmtpConfig.AuthType
import github.jk1.smtpidea.components.SmtpServerComponent
import javax.swing.JPanel
import java.awt.BorderLayout
import github.jk1.smtpidea.config.SmtpConfig
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.BorderFactory
import javax.swing.border.EtchedBorder
import javax.swing.JLabel
import javax.swing.BoxLayout
import javax.swing.JComponent
import java.awt.Component

/**
 *
 */
public class ConfigurationDialog(val project: Project) : DialogWrapper(project, false){

    private val component = project.getComponent(javaClass<SmtpServerComponent>())

    private val portSpinner = JSpinner();
    private val launchOnStartup = JCheckBox("Launch on startup");

    private val authRadioGroup = EnumRadioGroup<AuthType>();
    private val noAuthenticationRadio = JRadioButton("Anonymous mode");
    private val authSupportedRadio = JRadioButton("Support optional authentication");
    private val authEnforcedRadio = JRadioButton("Require authentication");

    private val sslRadioGroup = EnumRadioGroup<TransportSecurity>();
    private val plainRadio = JRadioButton("Plain SMTP");
    private val startTlsSupportedRadio = JRadioButton("Support STARTTLS");
    private val startTlsEnforcedRadio = JRadioButton("Require STARTTLS");
    private val sslOnConnectRadio = JRadioButton("SSL/TLS on connect (SMTPS)");

    private val login = JTextField(20);
    private val password = JTextField(20);


    {
        setTitle("SMTP Server Configuration");
        init();
    }

    /**
     * {@inheritDoc}
     */
    protected override fun createCenterPanel() : JPanel {
        val contextPane = JPanel(BorderLayout());
        contextPane.add(createTopPanel(), BorderLayout.NORTH);
        contextPane.add(createAuthPane(), BorderLayout.WEST);
        contextPane.add(createSslPane(), BorderLayout.EAST);
        installCurrentConfigurationValues(component?.getState() as SmtpConfig);
        return contextPane;
    }

    private fun createTopPanel() : JPanel {
        val panel = JPanel();
        val lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "General"));
        panel.add(JLabel("Port: "));
        panel.add(portSpinner);
        panel.add(launchOnStartup);
        return panel;
    }

    private fun createAuthPane() : JPanel {
        val panel = JPanel();
        val lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setLayout(BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Authentication"));
        authRadioGroup.add(noAuthenticationRadio, SmtpConfig.AuthType.DISABLED);
        authRadioGroup.add(authSupportedRadio, SmtpConfig.AuthType.SUPPORTED);
        authRadioGroup.add(authEnforcedRadio, SmtpConfig.AuthType.ENFORCED);
        panel.add(noAuthenticationRadio);
        val listener = AuthenticationSetupActionListener();
        noAuthenticationRadio.addActionListener(listener);
        authSupportedRadio.addActionListener(listener);
        authEnforcedRadio.addActionListener(listener);
        panel.add(authSupportedRadio);
        panel.add(authEnforcedRadio);
        panel.add(wrap("Username: ", login));
        panel.add(wrap("Password: ", password));
        return panel;
    }

    private fun createSslPane() : JPanel {
        val panel = JPanel();
        val lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setLayout(BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "SSL/TLS"));
        sslRadioGroup.add(plainRadio, SmtpConfig.TransportSecurity.PLAINTEXT);
        sslRadioGroup.add(startTlsSupportedRadio, SmtpConfig.TransportSecurity.STARTTLS_SUPPORTED);
        sslRadioGroup.add(startTlsEnforcedRadio, SmtpConfig.TransportSecurity.STARTTLS_ENFORCED);
        sslRadioGroup.add(sslOnConnectRadio, SmtpConfig.TransportSecurity.SSL);
        panel.add(plainRadio);
        panel.add(startTlsSupportedRadio);
        panel.add(startTlsEnforcedRadio);
        panel.add(sslOnConnectRadio);
        return panel;
    }

    private fun wrap(labelText : String,  jComponent : JComponent) : JPanel {
        val panel = JPanel();
        panel.add(JLabel(labelText));
        panel.add(jComponent);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    /**
     * Installs current configuration values in UI controls
     *
     * @param configuration current plugin configuration
     */
    private fun installCurrentConfigurationValues(configuration : SmtpConfig) {
        portSpinner.setValue(configuration.port);
        launchOnStartup.setSelected(configuration.launchOnStartup);
        authRadioGroup.setSelected(configuration.authType);
        sslRadioGroup.setSelected(configuration.transportSecurity);
        login.setText(configuration.login);
        password.setText(configuration.password);
    }

    /**
     *  Saves selected configuration and closes configuration dialog
     */
    protected override fun doOKAction() {
        super.doOKAction();
        val config = SmtpConfig();
        config.port = portSpinner.getValue() as Int;
        config.authType = authRadioGroup.getSelected();
        config.transportSecurity = sslRadioGroup.getSelected();
        config.login = login.getText() as String;
        config.password = password.getText() as String;
        config.launchOnStartup = launchOnStartup.isSelected();
        component?.loadState(config);
    }

    /**
     * Disables login/password text fields if authentication is turned off
     */
    private inner class AuthenticationSetupActionListener : ActionListener {
        override fun actionPerformed(e: ActionEvent) {
            login.setEnabled(!noAuthenticationRadio.isSelected());
            password.setEnabled(!noAuthenticationRadio.isSelected());
        }
    }
}
