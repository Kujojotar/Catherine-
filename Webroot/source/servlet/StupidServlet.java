package source.servlet;

import annotation.WebServlet;
import core.servlet.Servlet;
import core.servlet.ServletConfig;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import lifecycle.MyLifecycle;
import exception.MyServletException;

@WebServlet(urlPatterns = {"/haha","/"})
public class StupidServlet implements Servlet, MyLifecycle {
    private String msg="hahhahahah";

    @Override
    public void init(ServletConfig config)throws MyServletException {

    }

    @Override
    public void service(ServletRequest request, ServletResponse response) {
        System.out.println("恭喜您到达了servlet");
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

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }
}
