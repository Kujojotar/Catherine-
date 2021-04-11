package Core.servlet;

import Core.util.Cookie;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyRequest implements ServletRequest{
    private Map<String,Object> attributes=new HashMap<>();
    private String characterEncoding;
    private int contentLength;
    private Socket socket;
    private RequestLine requestLine;
    private RequestHead requestHead;

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public void setRequestHead(RequestHead requestHead) {
        this.requestHead = requestHead;
    }

    @Override
    public void setAttribute(String name,Object value){
        attributes.put(name,value);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public String getContentEncoding() {
        return characterEncoding;
    }

    @Override
    public Cookie[] getCookies() {
        return (Cookie[])requestHead.getCookies().toArray();
    }

    @Override
    public Cookie getCookie(String key) {
        return requestHead.getCookie(key);
    }

    @Override
    public String getParameter(String name) {
        return requestLine.get(name);
    }

    @Override
    public Map getParameterMap() {
        return requestLine.getProperties();
    }

    @Override
    public void removeAttribute(String attribute) {
        attributes.remove(attribute);
    }

    @Override
    public String getUri(){
        return requestLine.getUri();
    }
}
