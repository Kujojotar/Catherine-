package Core.servlet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 一种更为优雅的处理报文头部的方式
 */
public class MyRequestLine {
    private String method;
    private String uri;
    private String httpProtocol;
    private String jsessionId;
    private Map<String,String> properties=new HashMap<>();
    private String queryString;
    private String schema;
    private InputStream input;
    private final char pause=' ';
    private final char end='\r';
    private boolean isLegal;
    private boolean done=false;

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getHttpProtocol() {
        return httpProtocol;
    }

    public String getJsessionId() {
        return jsessionId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getSchema() {
        return schema;
    }

    public InputStream getInput() {
        return input;
    }


    public char getPause() {
        return pause;
    }

    public char getEnd() {
        return end;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public MyRequestLine(InputStream input){
        this.input=input;
        isLegal=true;
    }

    public String readPause(){
        char[] arr=new char[1024];
        StringBuilder builder=new StringBuilder();
        int i=0;
        for(;i==0||i<1024&&arr[i-1]!=pause;i++){
            try {
                arr[i] = (char)input.read();
            }catch (Exception e){
                e.printStackTrace();
                i=-1;
                break ;
            }
        }
        for(int k=0;k<i-1;k++){
            builder.append(arr[k]);
        }
        return builder.toString();
    }

    public String readEnd(){
        char[] arr=new char[1024];
        StringBuilder builder=new StringBuilder();
        int i=0;
        for(;i==0||i<1024&&arr[i-1]!=end;i++){
            try {
                arr[i] = (char)input.read();
            }catch (Exception e){
                e.printStackTrace();
                i=-1;
                break ;
            }
        }
        for(int k=0;k<i-1;k++){
            builder.append(arr[k]);
        }
        return builder.toString();
    }

    public void processUri(){
        String requestUri=uri;
        String pattern=";jsessionid=";
        int a=-1;
        a=requestUri.indexOf("?");
        if(a!=-1){
            queryString=requestUri.substring(a+1);
            requestUri=requestUri.substring(0,a);
            a=0;
            int b=queryString.indexOf("=",a);
            int c=queryString.indexOf("&",b);
            while(c!=-1){
                String key=queryString.substring(a,b);
                String value=queryString.substring(b+1,c);
                properties.put(key,value);
                a=c+1;
                b=queryString.indexOf("=",a);
                c=queryString.indexOf("&",b);
            }
            String key=queryString.substring(a,b);
            String value=queryString.substring(b+1);
            properties.put(key,value);
        }
        a=requestUri.indexOf(pattern);
        if(a!=-1){
            int b=requestUri.indexOf(";",a+pattern.length());
            if(b!=-1){
                jsessionId=requestUri.substring(a+pattern.length(),b);
                uri=requestUri.substring(0,a)+requestUri.substring(b+1);
            }
        }
    }

    public void init(){
        method=readPause();
        uri=readPause();
        httpProtocol=readEnd();
        processUri();
        done=true;
    }

    public void testResult(){
        System.out.println(method+uri+httpProtocol);
    }

    @Override
    public String toString() {
        return "MyRequestLine{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", httpProtocol='" + httpProtocol + '\'' +
                ", jsessionId='" + jsessionId + '\'' +
                ", properties=" + properties +
                ", queryString='" + queryString + '\'' +
                ", isLegal=" + isLegal +
                '}';
    }
}