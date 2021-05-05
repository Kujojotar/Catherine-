package core.filter;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import exception.MyServletException;

import java.io.IOException;

public interface Filter {
    public void init(FilterConfig filterConfig)throws MyServletException;
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException,MyServletException;
    public void destroy();
}
