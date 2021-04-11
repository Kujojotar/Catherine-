package Core.servlet;

import Core.util.Cookie;

import java.util.Map;

public class MyRequestFacade implements ServletRequest {
    private MyRequest myRequest;

    public MyRequestFacade(MyRequest myRequest){
        this.myRequest=myRequest;
    }

    @Override
    public void setAttribute(String name, Object value) {
        myRequest.setAttribute(name,value);
    }

    @Override
    public Object getAttribute(String key) {
        return myRequest.getAttribute(key);
    }

    @Override
    public String getContentEncoding() {
        return myRequest.getContentEncoding();
    }

    @Override
    public Cookie[] getCookies() {
        return myRequest.getCookies();
    }

    @Override
    public Cookie getCookie(String key) {
        return myRequest.getCookie(key);
    }

    @Override
    public String getParameter(String name) {
        return myRequest.getParameter(name);
    }

    @Override
    public Map getParameterMap() {
        return myRequest.getParameterMap();
    }

    @Override
    public void removeAttribute(String attribute) {
        myRequest.removeAttribute(attribute);
    }

    @Override
    public String getUri() {
        return myRequest.getUri();
    }
}
