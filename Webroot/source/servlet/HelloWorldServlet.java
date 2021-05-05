package source.servlet;

import annotation.WebServlet;
import core.servlet.Servlet;
import core.servlet.ServletConfig;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import exception.MyServletException;

import java.io.PrintWriter;

@WebServlet(urlPatterns = "/hello")
public class HelloWorldServlet implements Servlet {
    @Override
    public void init(ServletConfig config)throws MyServletException {

    }

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        response.println("Hello world!");
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
