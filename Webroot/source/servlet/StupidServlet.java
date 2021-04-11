package source.servlet;

import Annotation.WebServlet;
import Core.servlet.Servlet;
import Core.servlet.ServletConfig;
import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;
import Lifecycle.MyLifecycle;

@WebServlet(urlPatterns = {"/haha","/"})
public class StupidServlet implements Servlet, MyLifecycle {
    private String msg="hahhahahah";

    @Override
    public void init() {

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
