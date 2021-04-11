package source.filter;

import Annotation.WebFilter;
import Core.filter.Filter;
import Core.filter.FilterChain;
import Core.filter.FilterConfig;
import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;
import Exception.MyServletException;

import java.io.IOException;

@WebFilter(urls= {"/boy","/"})
public class LazyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws MyServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, MyServletException {
        System.out.println("44444");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
