package Core.session;


import Core.container.Container;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**、
 * @author james
 * Session管理器的实现类
 * ManagerBase实现了对一个简单session map的增删改查
 */
public class StandardManager extends ManagerBase implements Runnable{
    //关联的父容器
    private Container container;
    //等待的时间间隔，之前脑抽写成了过期时间
    private int maxInactiveInterval;
    //判断线程是否停止
    private boolean threadIsDone=false;
    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container=container;
    }

    @Override
    public int getMaxInactiveInterval(int interval) {
        return maxInactiveInterval;
    }

    @Override
    public Session createSession() {
        return null;
    }

    /**
     * 将session写入缓存之中
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Override
    public void load() throws ClassNotFoundException, IOException {
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("C:\\Users\\Jonny\\Desktop\\jessicat\\src\\mycat\\session\\session.ser"));
        oos.writeObject(sessions);
        oos.flush();
    }

    /**
     * 将session从缓存中读取
     * @throws IOException
     */
    @Override
    public void unload() throws IOException {
        FileInputStream input=new FileInputStream("C:\\Users\\Jonny\\Desktop\\jessicat\\src\\mycat\\session\\session.ser");
        ObjectInputStream objectInputStream=new ObjectInputStream(input);
        try {
            Map<String, Session> sessions = (HashMap<String, Session>) objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(sessions);
    }

    public void run(){
        while(!threadIsDone){
            threadSleep();
            processExpires();
        }
    }

    public void threadSleep(){
        synchronized (this){
            try{
                this.wait(maxInactiveInterval*1000);
            }catch (InterruptedException e){
            }
        }
    }

    public void processExpires(){
        Session[] res=findSessions();
        for(int i=0;i<sessions.size();i++){
            if(System.currentTimeMillis()-res[i].getAccessedTime()>=res[i].getMaxInactiveInterval()){
                res[i].expire();
            }
        }
    }
}
