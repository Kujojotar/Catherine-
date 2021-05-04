package core.connector;

import core.container.Container;
import core.processor.HttpProcessor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class HttpConnector implements Runnable {
    private Stack<HttpProcessor> processors=new Stack();
    private Container container;

    private ServerSocket serverSocket;
    protected int minProcessors=5;
    protected int maxProcessors=20;
    private int curProcessors=0;
    private boolean stopped=false;

    public HttpConnector(Container container){
        this.container=container;
        try {
            serverSocket = new ServerSocket(8081);
        }catch (Exception e){
            e.printStackTrace();
            serverSocket=null;
        }
    }

    public int getMinProcessors() {
        return minProcessors;
    }

    public void setMinProcessors(int minProcessors) {
        this.minProcessors = minProcessors;
    }

    public int getMaxProcessors() {
        return maxProcessors;
    }

    public void setMaxProcessors(int maxProcessors) {
        this.maxProcessors = maxProcessors;
    }

    public void start(){
        while(curProcessors<minProcessors){
            if((maxProcessors>0)&&(curProcessors>=maxProcessors)){
                break ;
            }
            HttpProcessor processor=new HttpProcessor();
            processor.setConnector(this);
            processor.setContainer(container);
            curProcessors++;
            recycle(processor);
        }
    }

    public void recycle(HttpProcessor processor){
        processors.push(processor);
    }
    @Override
    public void run() {
        while(!stopped){
            Socket socket=null;
            try{
                socket=serverSocket.accept();
                HttpProcessor processor=processors.pop();
                if(processor==null){
                    curProcessors++;
                    if(curProcessors>=maxProcessors){
                        System.out.println("请求超出了上线");
                        throw new Exception();
                    }else {
                        processor = new HttpProcessor();
                        processor.setConnector(this);
                        processor.setContainer(container);
                    }
                }
                //使用尼玛的线程池，老子就nm在创建线程池™，哈皮阿里
                Thread thread=new Thread(processor);
                thread.start();
                processor.assign(socket);
            }catch (Exception e){

            }
        }
    }
}
