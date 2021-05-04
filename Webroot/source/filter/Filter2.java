package source.filter;

import Annotation.WebFilter;
import core.filter.Filter;
import core.filter.FilterChain;
import core.filter.FilterConfig;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import Exception.MyServletException;

import java.io.IOException;

@WebFilter(urls = {"//","/haha"})
public class Filter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws MyServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, MyServletException {
        System.out.println("又一个Filter过滤器!");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
