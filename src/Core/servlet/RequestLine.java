package Core.servlet;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    private String method;
    private String uri;
    private String httpProtocol;
    private String jsessionId;
    private Map<String,String> properties=new HashMap<>();
    private String queryString;
    private String schema;

    private boolean isSessionSet=false;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpProtocol() {
        return httpProtocol;
    }

    public void setHttpProtocol(String httpProtocol) {
        this.httpProtocol = httpProtocol;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public void setJsessionId(String jsessionId) {
        this.jsessionId = jsessionId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setIsSessionSet(boolean isSessionSet){
        this.isSessionSet=isSessionSet;
    }

    public void put(String key,String val){
        properties.put(key,val);
    }

    public String get(String key){
        return properties.get(key);
    }

    @Override
    public String toString() {
        return "HttpLine{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", httpProtocol='" + httpProtocol + '\'' +
                ", jsessionId='" + jsessionId + '\'' +
                ", properties=" + properties +
                ", queryString='" + queryString + '\'' +
                ",schema='"+schema+'\''+
                '}';
    }
}
