package github.jk1.smtpidea.ui

import java.awt.BorderLayout
import com.intellij.ui.table.JBTable
import github.jk1.smtpidea.store.InboxFolder
import com.intellij.ui.components.JBScrollPane
import github.jk1.smtpidea.actions.ActionRegistry

/**
 *  "POP3 Server" tab contents for main tool window
 */
class Pop3ServerContent : BaseContent("POP3 Server") {
     {
        val mailTable = JBTable(InboxFolder);
        mailTable.setStriped(true);
        mailTable.setExpandableItemsEnabled(true);
        add(JBScrollPane(mailTable), BorderLayout.CENTER);
        add(createActionsButtonPane(
                ActionRegistry.createMailAction(),
                ActionRegistry.startPop3(),
                ActionRegistry.stopPop3(),
                ActionRegistry.configure(),
                ActionRegistry.clearInboxFolder()), BorderLayout.WEST);
    }
}