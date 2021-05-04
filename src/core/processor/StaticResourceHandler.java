package core.processor;

import Constants.Constants;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;

import java.io.*;

public class StaticResourceHandler {
    private ServletRequest request;
    private ServletResponse response;
    private static final int BYTES_LENGTH=1024;

    public StaticResourceHandler(ServletRequest request,ServletResponse response){
        this.request=request;
        this.response=response;
    }

    public void parse(){
        String fileName=request.getUri();
        fileName=fileName.substring(fileName.lastIndexOf("/"));
        File file=new File(Constants.WEB_ROOT+fileName);
        PrintWriter writer = response.getPrintWriter();
        try {
            if (!file.exists()) {
                writer.println("HTTP/1.1 404 NOT FOUND");
                writer.println("Content-Type:text/html;charset:UTF-8");
                writer.println();
                return;
            } else {
                writer.println("HTTP/1.0 200 OK");
                if (fileName.endsWith(".html")) {
                    writer.println("Content-Type:text/html;charset:UTF-8");
                    writer.println();
                    writeFile(writer,file);
                } else if (fileName.endsWith("xml")) {
                    writer.println("Content-Type:text/xml;charset:UTF-8");
                    writer.println();
                    writeFile(writer,file);
                } else if (fileName.endsWith("gif")) {
                    writer.println("Content-Type:image/gif;charset:UTF-8");
                    writer.println();
                    writeFile(writer,file);
                } else if (fileName.endsWith("jpeg")) {
                    writer.println("Content-Type:image/jpeg;charset:UTF-8");
                    writer.println();
                    writeFile(writer,file);
                } else if (fileName.endsWith("png")) {
                    writer.println("Content-Type:image/png;charset:UTF-8");
                    writer.println();
                    writeFile(writer,file);
                } else {
                    writer.println("对不起，我们不支持相关资源");
                    writer.println();
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            writer.println("系统GG了");
        }catch (IOException e){
            e.printStackTrace();
            writer.println("系统GG了");
        }
    }

    public void writeFile(PrintWriter writer,File file)throws FileNotFoundException,IOException {
        byte[] bytes=new byte[BYTES_LENGTH];
        boolean stop=false;
        InputStream inputStream = new FileInputStream(file);
        while (!stop) {
            int c = -1;
            int i = 0;
            for (; i < BYTES_LENGTH; i++) {
                 c=inputStream.read();
                 if(c==-1){
                     stop=true;
                     break;
                 }else{
                     bytes[i]=(byte)c;
                 }
            }
            for(int k=0;k<i;k++){
                writer.write((char)bytes[k]);
            }
        }
    }

}
