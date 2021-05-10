package core.processor;

import constants.Constants;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

public class StaticResourceHandler {
    private ServletRequest request;
    private ServletResponse response;
    private DataOutputStream dataOutputStream;
    private static final int BYTES_LENGTH=1024;

    public StaticResourceHandler(ServletRequest request,ServletResponse response){
        this.request=request;
        this.response=response;
        dataOutputStream=new DataOutputStream(response.getOutputStream());
    }

    public void parse(){
        String fileName=request.getUri();
        fileName=fileName.substring(fileName.lastIndexOf("/")+1);
        File file=new File(Constants.WEB_ROOT+File.separator+"resource"+File.separator+fileName);
        PrintWriter writer = response.getPrintWriter();
        try {
            //C:\Users\Jonny\Desktop\Catherine\Webroot\resource\img.png
            if (!file.exists()) {
                writer.println("HTTP/1.1 404 NOT FOUND");
                writer.println("Content-Type:text/html;charset:UTF-8");
                writer.println();
                return;
            } else {
                if (fileName.endsWith(".html")) {
                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type:text/html;charset:UTF-8");
                    writer.println();
                    writeFile(writer,file);
                } else if(fileName.endsWith(".txt")){
                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type:text/html;charset:UTF-8");
                    writer.println();
                    writeFile(writer,file);
                } else if (fileName.endsWith("xml")) {
                    writer.println("Content-Type:text/xml;charset:UTF-8");
                    writer.println();
                    writeFile(writer,file);
                } else if (fileName.endsWith("gif")) {
                    writePicture(response,file);
                } else if (fileName.endsWith("jpeg")) {
                    writePicture(response,file);
                } else if (fileName.endsWith("png")) {
                    writePicture(response,file);
                } else {
                    writer.println("Content-Type:text/html;charset:UTF-8");
                    writer.println();
                    writer.println("对不起，我们不支持相关资源");
                }
                writer.close();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            writer.println("系统GG了");
        }catch (IOException e){
            e.printStackTrace();
            writer.println("系统GG了");
        }
    }

    private void writeFile(PrintWriter writer,File file)throws FileNotFoundException,IOException {
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

    private void writeFile(OutputStream writer,File file)throws FileNotFoundException,IOException {
        byte[] chars=new byte[BYTES_LENGTH];
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
                    writer.write(c);
                }
            }
        }
    }

    public void writePicture(ServletResponse response,File file)throws IOException{
        DataOutputStream dataOutputStream=new DataOutputStream(response.getOutputStream());
        dataOutputStream.writeBytes("HTTP/1.1 200 OK\r\n");
        dataOutputStream.writeBytes("Content-Type:image/png; charset:UTF-8\r\n");
        dataOutputStream.writeBytes("Connection: Keep-Alive\r\n");
        dataOutputStream.writeBytes("\r\n");
        FileInputStream fis=new FileInputStream(file);
        int a=-1;
        byte[] bytes=new byte[1024];
        while((a= fis.read(bytes))!=-1){
            dataOutputStream.write(bytes,0,a);
        }
    }

}
