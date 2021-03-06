package core.servlet;
import exception.MyServletException;
public interface Servlet {
    void init(ServletConfig config)throws MyServletException;
    void service(ServletRequest request,ServletResponse response);
    void destroy();
    String getServletInfo();
    ServletConfig getServletConfig();
}
