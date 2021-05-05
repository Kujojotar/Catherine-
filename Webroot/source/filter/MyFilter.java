package source.filter;

import annotation.WebFilter;
import core.filter.Filter;
import core.filter.FilterChain;
import core.filter.FilterConfig;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import exception.MyServletException;

import java.io.IOException;

@WebFilter(urls = "/hello")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws MyServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, MyServletException {
         response.println("Filter do !    ");
         chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
