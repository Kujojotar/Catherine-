package Core.filter;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;
import Exception.MyServletException;

import java.io.IOException;

public interface Filter {
    public void init(FilterConfig filterConfig)throws MyServletException;
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException,MyServletException;
    public void destroy();
}
