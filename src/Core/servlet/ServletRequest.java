package Core.servlet;

import Core.util.Cookie;

import java.util.Map;

public interface ServletRequest {
    void setAttribute(String name,Object value);

    Object getAttribute(String key);

    String getContentEncoding();

    Cookie[] getCookies();

    Cookie getCookie(String key);

    public String getParameter(String name);

    public Map getParameterMap();

    public void removeAttribute(String attribute);

    public String getUri();
}
