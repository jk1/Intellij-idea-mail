package github.jk1.smtpidea.server.util;

import java.util.List;

/** Passes the request and response along a series of filters
 *
 *  By default the MailServer loads a single filter which executes the appropriate handler
 *
 *  @author Evgeny Naumenko
 */
public class FilterChain {


    final List<Filter> filters;
    final Filter terminal;
    int pos = 0;

    public FilterChain(List<Filter> filters, Filter terminal) {
        this.filters =  filters;
        this.terminal = terminal;

    }

    public void doEvent(Event event) {
        if( pos < filters.size() ) {
            Filter filter = filters.get(pos++);
            if( filter != null ) {
                filter.doEvent(this,event);
                return ;
            }
        }
        if( terminal != null ) {
            terminal.doEvent(this, event);
        }
    }
}
