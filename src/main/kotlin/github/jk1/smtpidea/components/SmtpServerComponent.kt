package github.jk1.smtpidea.components

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.components.StoragePathMacros.*
import com.intellij.openapi.components.StorageScheme.*
import github.jk1.smtpidea.config.SmtpConfig;
import github.jk1.smtpidea.server.smtp.SmtpServerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Evgeny Naumenko
 */
State(
        name = "SmtpServerComponent",
        storages = array(
                Storage(id = "default", file = "${PROJECT_FILE}"),
                Storage(id = "dir", file = "${PROJECT_CONFIG_DIR}/smtp.xml", scheme = DIRECTORY_BASED)
        )
)
public class SmtpServerComponent(val project: Project) : AbstractProjectComponent(project), PersistentStateComponent<SmtpConfig> {

    private var configuration: SmtpConfig = SmtpConfig()
    private var server: SmtpServerManager? = null

    public override fun disposeComponent() = server?.stopServer()

    public override fun initComponent() {
        server = ServiceManager.getService(project, javaClass<SmtpServerManager>())
        if (server != null) {
            server!!.configuration = configuration  // http://youtrack.jetbrains.com/issue/KT-1213
            if (configuration.launchOnStartup) {
                server?.startServer()
            }
        }
    }

    public override fun getComponentName(): String = "SmtpServerComponent"

    public override fun getState(): SmtpConfig = configuration

    public override fun loadState(smtpConfig: SmtpConfig?) {
        //may be called any time, check everything
        if (smtpConfig != null) {
            configuration = smtpConfig
            if (server != null) {
                server!!.configuration = smtpConfig
            }
        }
    }
}
