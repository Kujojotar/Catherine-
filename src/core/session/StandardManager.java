package core.session;


import core.container.Container;

import java.io.*;
import java.util.UUID;


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
        StandardSession session=new StandardSession(this);
        String sessionId= UUID.randomUUID().toString().replaceAll("-","");
        session.setId(sessionId);
        add(session);
        return session;
    }

    /**
     * 将session从缓存中读取
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Override
    public void load() throws ClassNotFoundException, IOException {
        // TODO:装载持久化缓存中的session 2021/4/22
        /*
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("C:\\Users\\Jonny\\Desktop\\jessicat\\src\\mycat\\session\\session.ser"));
        oos.writeObject(sessions);
        oos.flush();
         */
    }

    /**
     * 将session装载到缓存中
     * @throws IOException
     */
    @Override
    public void unload() throws IOException {
        // TODO:将session装入持久化的缓存中 2021/4/22
        /*
        FileInputStream input=new FileInputStream("C:\\Users\\Jonny\\Desktop\\jessicat\\src\\mycat\\session\\session.ser");
        ObjectInputStream objectInputStream=new ObjectInputStream(input);
        try {
            Map<String, Session> sessions = (HashMap<String, Session>) objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(sessions);
         */
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
