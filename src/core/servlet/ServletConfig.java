package core.servlet;

import javax.naming.Context;
import java.util.Enumeration;

public interface ServletConfig {
    public String getServletName();
    public Context getServletContext();
    public String getInitParameter(String name);
    public Enumeration<String> getInitParameterNames();
}
