package Core.filter;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;

public interface FilterChain {
    public void doFilter(ServletRequest request, ServletResponse response);
}
