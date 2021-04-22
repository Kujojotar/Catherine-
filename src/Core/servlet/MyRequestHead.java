package Core.servlet;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 比以前更加完善的RequestHead
 * 虽然也没有完善多少
 */
public class MyRequestHead {
    private InputStream inputStream;
    private String host;
    private String connection;
    private String[] accept;
    private Map<String,String> cookies;
    private final char pause=':';
    private final char end='\r';
    private boolean parseDown=false;
    private boolean cookieEnd=false;
    private char[] arr=new char[1024];

    public MyRequestHead(InputStream inputStream){
        this.inputStream=inputStream;
        cookies=new HashMap<>();
    }

    public String readPause(){
        StringBuilder builder=new StringBuilder();
        int i=0;
        int j;
        for(;i==0||i<1024&&arr[i-1]!=pause;i++){
            try {
                j=inputStream.read();
                if(j<0){
                    if(i==0){
                        return null;
                    }else{
                        i++;
                        break;
                    }
                }
                arr[i] = (char)j;
            }catch (Exception e){
                e.printStackTrace();
                i=-1;
                break ;
            }
            if(i>=1&&(arr[i-1]==end||arr[i-1]=='\n')){
                return null;
            }
        }
        for(int k=0;k<i-1;k++){
            builder.append(arr[k]);
        }
        return builder.toString();
    }

    public String readEnd(){
        StringBuilder builder=new StringBuilder();
        int i=0;
        for(;i==0||i<1024&&arr[i-1]!=end;i++){
            try {
                arr[i] = (char)inputStream.read();
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

    private String read(char end){
        StringBuilder builder=new StringBuilder();
        int i=0;
        for(;i==0||i<1024&&arr[i-1]!=end;i++){
            try {
                arr[i] = (char)inputStream.read();
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

    private String readUntilCookieEnd(){
        StringBuilder builder=new StringBuilder();
        int i=0;
        int j=-1;
        while(i<1024){
            try{
                j=inputStream.read();
                arr[i]=(char)j;
                i++;
                if(j==':'){
                    break;
                }
                if(j=='\r'){
                    cookieEnd=true;
                    break;
                }
            }catch (IOException e){
                cookieEnd=true;
                return null;
            }
        }
        for(int k=0;k<i-1;k++){
            builder.append(arr[k]);
        }
        return builder.toString();
    }


    public void parseCookie(){
        while(!cookieEnd){
            String key=read('=');
            String value=readUntilCookieEnd();
            cookies.put(key,value);
        }
    }

    public void parseRequestHead(){
        try {
            inputStream.read();
        }catch (IOException e){
            e.printStackTrace();
        }
        String field=readPause();
        int test=-1;
        while(!parseDown) {
            try {
                if(field==null){
                    parseDown=true;
                    break;
                }
                test = inputStream.read();
            }catch (IOException e){
                e.printStackTrace();
                parseDown=true;
            }
            if (test==-1||field == null) {
                parseDown = true;
            } else if (field.equals("Host")) {
                host=readEnd();
            } else if (field.equals("Connection")) {
                connection=readEnd();
            } else if (field.equals("Accept")) {
                // TODO:懒得解析这玩意了，呵呵呵 2021/4/21
                readEnd();
            } else if (field.equals("Cookie")) {
                parseCookie();
            }else{
                readEnd();
            }
            try {
               inputStream.read();
            }catch (IOException e){
                e.printStackTrace();
            }
            field=readPause();
        }
    }

    @Override
    public String toString() {
        return "MyRequestHead{" +
                "host='" + host + '\'' +
                ", connection='" + connection + '\'' +
                ", cookies=" + cookies.toString() +
                '}';
    }
}
