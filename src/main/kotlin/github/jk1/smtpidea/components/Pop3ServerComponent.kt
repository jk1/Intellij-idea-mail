package github.jk1.smtpidea.components

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

import com.intellij.openapi.components.StoragePathMacros.*
import github.jk1.smtpidea.server.pop3.Pop3ServerManager
import github.jk1.smtpidea.config.Pop3Config

/**
 * Manages embedded POP3 server
 *
 * @author Evgeny Naumenko
 */
State(
        name = "Pop3ServerComponent",
        storages = array(
                Storage(id = "default", file = StoragePathMacros.PROJECT_FILE),
                Storage(id = "dir", file = "\$PROJECT_CONFIG_DIR\$" + "/pop3.xml", scheme = StorageScheme.DIRECTORY_BASED)
        )
)
public class Pop3ServerComponent(val project: Project) : AbstractProjectComponent(project), PersistentStateComponent<Pop3Config> {

    private var configuration: Pop3Config = Pop3Config()
    private var server: Pop3ServerManager? = null



    public override fun disposeComponent(): Unit {
        server?.stopServer()
    }


    public override fun initComponent(): Unit {
        server = ServiceManager.getService(project, javaClass<Pop3ServerManager>())
        server?.setConfiguration(configuration)
        if (configuration.launchOnStartup) {
            server?.startServer()
        }

    }

    public override fun getComponentName(): String {
        return "Pop3ServerComponent"
    }

    public override fun getState(): Pop3Config {
        return configuration
    }

    public override fun loadState(pluginConfiguration: Pop3Config?) {
        //may be called any time, check everything
        if (pluginConfiguration != null) {
            configuration = pluginConfiguration
            server?.setConfiguration(pluginConfiguration)

        }
    }

}

