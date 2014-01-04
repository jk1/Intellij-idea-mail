package github.jk1.smtpidea.ui

import javax.swing.JPanel
import java.awt.BorderLayout
import com.intellij.ui.table.JBTable
import github.jk1.smtpidea.store.InboxFolder
import com.intellij.ui.components.JBScrollPane
import github.jk1.smtpidea.actions.StartServerAction

/**
 *
 */
class Pop3ServerContent : BaseContent("POP3 Server") {
     {
        val mailTable = JBTable(InboxFolder);
        mailTable.setStriped(true);
        mailTable.setExpandableItemsEnabled(true);
        add(JBScrollPane(mailTable), BorderLayout.CENTER);
        add(createActionsButtonPane(StartServerAction.POP3), BorderLayout.WEST);
    }
}