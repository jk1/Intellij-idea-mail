package github.jk1.smtpidea.server.util;

/**
 * @author Evgeny Naumenko
 */
public interface Filter {

    public void doEvent(FilterChain chain, Event event);

}
