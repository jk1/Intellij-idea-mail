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
                ActionRegistry.START_POP3,
                ActionRegistry.STOP_POP3,
                ActionRegistry.CONFIGURE,
                ActionRegistry.CLEAR_INBOX_FOLDER), BorderLayout.WEST);
    }
}