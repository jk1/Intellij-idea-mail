package github.jk1.smtpidea.components;

import com.intellij.openapi.components.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */

@State(
        name = "SmtpConfiguration",
        storages = {
                @Storage(id = "smtp_plugin.config", file = StoragePathMacros.APP_CONFIG + ".config")
        }
)
public class SmtpServerApplicationComponent implements
        ApplicationComponent, PersistentStateComponent<PluginConfiguration> {


    public SmtpServerApplicationComponent() {
    }

    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "SmtpServerApplicationComponent";
    }

    @Nullable
    @Override
    public PluginConfiguration getState() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void loadState(PluginConfiguration pluginConfiguration) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
