package source.servlet;

import Annotation.WebServlet;
import Core.servlet.Servlet;
import Core.servlet.ServletConfig;
import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;
import Exception.MyServletException;

import java.io.PrintWriter;

@WebServlet(urlPatterns = "/hello")
public class HelloWorldServlet implements Servlet {
    @Override
    public void init(ServletConfig config)throws MyServletException {

    }

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        PrintWriter writer= response.getPrintWriter();
        writer.println("Hello world!");
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
