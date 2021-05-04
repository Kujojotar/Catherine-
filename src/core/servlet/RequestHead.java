package core.servlet;

import core.util.Cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RequestHead {
    private Map<String,String> properties;
    private List<Cookie> cookies;

    public RequestHead(){
        properties=new HashMap<>();
        cookies=new ArrayList<>();
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String get(String key){
        return properties.get(key);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void putProperties(String key,String value){
        properties.put(key, value);
    }

    public void addCookie(String key,String value){
        Cookie cookie=new Cookie(key,value);
        cookies.add(cookie);
    }

    @Override
    public String toString() {
        return "RequestHead{" +
                "properties=" + properties +
                ", cookies=" + cookies +
                '}';
    }

    public Cookie getCookie(String key) {
        for(int i=0;i<cookies.size();i++){
            if(key.equals(cookies.get(i).getKey())){
                return cookies.get(i);
            }
        }
        return null;
    }
}
