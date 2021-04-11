package source.filter;

import Annotation.WebFilter;
import Annotation.WebServlet;
import Core.filter.Filter;
import Core.filter.FilterChain;
import Core.filter.FilterConfig;
import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;
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
