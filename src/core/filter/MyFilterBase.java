package core.filter;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import Exception.MyServletException;

import java.io.IOException;

public class MyFilterBase implements Filter {
    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws MyServletException {
        this.filterConfig=filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, MyServletException {

    }

    @Override
    public void destroy() {

    }
}
