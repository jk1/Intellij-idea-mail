package github.jk1.smtpidea.components

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

import com.intellij.openapi.components.StoragePathMacros.*
import com.intellij.openapi.components.StorageScheme.*

/**
 *
 * @author Evgeny Naumenko
 */
State(
        name = "Pop3ServerComponent",
        storages = array(
                Storage(id = "default", file = PROJECT_FILE),
                Storage(id = "dir", file = PROJECT_CONFIG_DIR + "/pop3.xml", scheme = DIRECTORY_BASED)
        )
)
public class Pop3ServerComponent(project: Project) : AbstractProjectComponent(project), PersistentStateComponent<PluginConfiguration> {

    override fun getState(): PluginConfiguration? {
        return null;
    }
    override fun loadState(config: PluginConfiguration?) {
    }
}

