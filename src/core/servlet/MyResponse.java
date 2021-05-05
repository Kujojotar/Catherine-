package core.servlet;

import constants.HttpStatusWithMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyResponse implements ServletResponse{
    private OutputStream outputStream;
    private PrintWriter writer;
    private String httpVersion;
    private Integer status;
    private Map<String,String> headers;
    private StringBuilder responseBody;

    public MyResponse(){
        outputStream=null;
        writer=null;
        httpVersion="HTTP/1.1";
        status=200;
        headers=new HashMap<>();
        responseBody=new StringBuilder();
        init();
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        try {
            writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(){
        headers.put("Content-Type:","text/html;Charset: UTF-8");
        //headers.put("Charset:","UTF-8");
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public MyResponse(OutputStream outputStream){
        this();
        setOutputStream(outputStream);
    }

    public PrintWriter getPrintWriter(){
        return writer;
    }

    public void setHeader(String header,String content){
        headers.put(header,content);
    }

    public void println(String str){
        responseBody.append(str+"\n");
    }

    public void sendRedirect(String destination) {
        status = 302;
        headers.put("Location:",destination);
        writeToSocket();
    }

    public void writeToSocket(){
        //将响应头写入请求行
        writer.println(httpVersion+" "+status+" "+ HttpStatusWithMessage.getMessage(status));

        //将响应头写入socket
        Iterator<Map.Entry<String,String>> iterator=headers.entrySet().iterator();
        iterator.forEachRemaining(x->writer.println(x.getKey()+" "+x.getValue()));
        writer.println();

        //将响应报文写入socket
        String message= responseBody.toString();
        writer.println(message);
        writer.flush();
        writer.close();
    }
}
