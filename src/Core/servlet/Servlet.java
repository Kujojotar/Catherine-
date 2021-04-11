package Core.servlet;

public interface Servlet {
    void init();
    void service(ServletRequest request,ServletResponse response);
    void destroy();
    String getServletInfo();
    ServletConfig getServletConfig();
}
