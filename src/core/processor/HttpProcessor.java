package core.processor;

import core.connector.HttpConnector;
import core.container.Container;
import core.servlet.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


/**
 * @author james
 * 解析HTTP请求的类
 */
public class HttpProcessor implements Runnable {
    private Socket socket;
    private String httpMessage;
    private static final int DEFAULT_LENGTH=1024;
    private int length;
    private RequestLine line;
    private RequestHead head;
    private boolean available=false;
    private HttpConnector connector;
    private Container container;

    public HttpProcessor(){
    }

    public void setSocket(Socket socket){
        this.socket=socket;
    }

    //初始化代码
    public void init() {
        InputStream input = null;
        StringBuilder builder=new StringBuilder();
        try {
            input = socket.getInputStream();
            StringBuffer request=new StringBuffer(2048);
            int i;
            byte[] buffer=new byte[2048];
            try{
                i=input.read(buffer);
            }catch(IOException e){
                e.printStackTrace();
                i=-1;
            }
            for(int j=0;j<i;j++){
                request.append((char)buffer[j]);
            }
            httpMessage=request.toString();
            length=httpMessage.trim().length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseHttpLine(){
        line=new RequestLine();
        String firstLine;
        int a=httpMessage.indexOf("\r");
        firstLine=httpMessage.substring(0,a+2);
        a=firstLine.indexOf(" ");
        String method=firstLine.substring(0,a);
        firstLine=firstLine.substring(a+1);
        line.setMethod(method);
        a=firstLine.indexOf(" ");
        String uri=firstLine.substring(0,a);
        firstLine=firstLine.substring(a+1);
        a=uri.indexOf("?");
        if(a!=-1){
            String queryStr=uri.substring(a+1);
            String copy=uri.substring(a+1);
            line.setQueryString(queryStr);
            uri=uri.substring(0,a);
            String key=null;
            String value=null;
            int b=-1;
            while((a=copy.indexOf("&"))!=-1){
                b=copy.indexOf("=");
                key=copy.substring(0,b);
                value=copy.substring(b+1,a);
                line.put(key,value);
                copy=copy.substring(a+1);
            }
            a=copy.length();
            b=copy.indexOf("=");
            key=copy.substring(0,b);
            value=copy.substring(b+1,a);
            line.put(key,value);
        }
        String match=";jsessionid=";
        if((a=uri.indexOf(match))!=-1){
            String form=uri.substring(0,a);
            line.setIsSessionSet(true);
            int b=uri.indexOf(";",a+1);
            String sessionId=uri.substring(a+";jsessionid=".length(),b);
            line.setJsessionId(sessionId);
            uri=form+uri.substring(b+1);
        }else{
            line.setJsessionId(null);
            line.setIsSessionSet(false);
        }
        line.setUri(uri);
        String protocol=firstLine.substring(0,firstLine.indexOf("\r"));
        line.setHttpProtocol(protocol);
        String schema=protocol.substring(0,protocol.indexOf("/"));
        line.setSchema(schema);
    }

    public void parseRequestHead(){
        String requestHead=httpMessage.substring(httpMessage.indexOf("\r")+2);
        String singleLine=requestHead.substring(0,requestHead.indexOf("\r")+2);
        int end=singleLine.length();
        RequestHead head=new RequestHead();
        while(end>=0&&singleLine.indexOf(":")!=-1){
            if(singleLine.contains("Cookie")){
                singleLine=singleLine.substring(8);
                int a=-1;
                int b=-1;
                while((a=singleLine.indexOf("="))!=-1){
                    String key=singleLine.substring(0,a);
                    String value;
                    if((b=singleLine.indexOf(";"))!=-1){
                        value=singleLine.substring(a+1,b);
                    }else{
                        value=singleLine.substring(a+1);
                        head.addCookie(key,value);
                        break;
                    }
                    head.addCookie(key,value);
                    singleLine=singleLine.substring(singleLine.indexOf(" ")+1);
                }
            }else{
                int a=singleLine.indexOf(":");
                String key=singleLine.substring(0,a);
                String value=singleLine.substring(a+2,end);
                head.putProperties(key,value);
            }
            requestHead=requestHead.substring(requestHead.indexOf("\r")+2);
            singleLine=requestHead.substring(0,requestHead.indexOf("\r"));
            end=singleLine.length();
        }
        this.head=head;
    }

    public RequestLine getLine(){
        return line;
    }

    public RequestHead getHead() {
        return head;
    }

    public synchronized void assign(Socket socket){
        while(available){
            try{
                wait();
            }catch(InterruptedException e){

            }
        }
        this.socket=socket;
        available=true;
        notifyAll();
    }

    private synchronized Socket await() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        Socket socket = this.socket;
        available = false;
        notifyAll();
        return socket;
    }

    @Override
    public void run(){
        while(true){
            Socket socket=await();
            if(socket==null)
                continue;
            try{
                process(socket);
            }catch(Throwable t){
                break;
            }
            connector.recycle(this);
        }
    }

    public HttpConnector getConnector() {
        return connector;
    }

    public void setConnector(HttpConnector connector) {
        this.connector = connector;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    private void process(Socket socket)throws Throwable {
        init();
        parseHttpLine();
        parseRequestHead();
        MyResponse response=new MyResponse();
        response.setOutputStream(socket.getOutputStream());
        //response.write();
        MyRequest request=new MyRequest();
        request.setRequestLine(line);
        request.setRequestHead(head);
        container.invoke(request,response);
    }
}
