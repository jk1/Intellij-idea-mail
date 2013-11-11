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

/**
 * @author Evgeny Naumenko
 */
public class ConfigurationDialog extends DialogWrapper {

    private SmtpServerComponent component;

    private JSpinner portSpinner = new JSpinner();
    private JCheckBox launchOnStartup = new JCheckBox("Launch on startup");

    private ButtonGroup authRadioGroup = new ButtonGroup();
    private JRadioButton noAuthenticationRadio = new JRadioButton("Anonymous mode");
    private JRadioButton authSupportedRadio = new JRadioButton("Support optional authentication");
    private JRadioButton authEnforcedRadio = new JRadioButton("Require authentication");

    private ButtonGroup sslRadioGroup = new ButtonGroup();
    private JRadioButton plainRadio = new JRadioButton("Plain SMTP");
    private JRadioButton startTlsSupportedRadio = new JRadioButton("Support STARTTLS");
    private JRadioButton startTlsEnforcedRadio = new JRadioButton("Require STARTTLS");
    private JRadioButton sslOnConnectRadio = new JRadioButton("SSL/TLS on connect (SMTPS)");

    private JTextField login = new JTextField(20);
    private JTextField password = new JTextField(20);

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
        JPanel contextPane = new JPanel(new BorderLayout());
        contextPane.add(createTopPanel(), BorderLayout.NORTH);
        contextPane.add(createAuthPane(), BorderLayout.WEST);
        contextPane.add(createSslPane(), BorderLayout.EAST);
        return contextPane;
    }

    private JPanel createTopPanel() {
        PluginConfiguration config = component.getState();
        JPanel panel = new JPanel();
        Border lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "General"));
        portSpinner.setValue(config.smtpConfig.port);
        panel.add(new JLabel("Port: "));
        panel.add(portSpinner);
        launchOnStartup.setSelected(config.launchOnStartup);
        panel.add(launchOnStartup);
        return panel;
    }

    private JPanel createAuthPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        Border lineBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Authentication"));
        authRadioGroup.add(noAuthenticationRadio);
        authRadioGroup.add(authSupportedRadio);
        authRadioGroup.add(authEnforcedRadio);
        panel.add(noAuthenticationRadio);
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
        sslRadioGroup.add(plainRadio);
        sslRadioGroup.add(startTlsSupportedRadio);
        sslRadioGroup.add(startTlsEnforcedRadio);
        sslRadioGroup.add(sslOnConnectRadio);
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
