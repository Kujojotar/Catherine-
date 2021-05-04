package source.servlet;

import Annotation.WebServlet;
import core.servlet.Servlet;
import core.servlet.ServletConfig;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import Exception.MyServletException;

@WebServlet(urlPatterns = "/jiji")
public class AnotherServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws MyServletException {

    }

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        System.out.println("这是一个干扰的Servlet");
    }

    @Override
    public void destroy() {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
}
