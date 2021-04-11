package Core.servlet;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyResponse implements ServletResponse{
    private OutputStream outputStream;
    private PrintWriter writer;

    public MyResponse(){
        outputStream=null;
        writer=null;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        try {
            writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public MyResponse(OutputStream outputStream){
        this.outputStream=outputStream;
        writer=new PrintWriter(new OutputStreamWriter(outputStream));
    }

    public PrintWriter getPrintWriter(){
        return writer;
    }

    public void write(){
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type:text/html;charset:UTF-8");
        writer.println();
    }

}
