package core.filter;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;

public interface FilterChain {
    public void doFilter(ServletRequest request, ServletResponse response);
}
