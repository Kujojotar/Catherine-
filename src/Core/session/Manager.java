package Core.session;


import Core.container.Container;

import java.io.IOException;


public interface Manager {
    public Container getContainer();
    public void setContainer(Container container);
    public int getMaxInactiveInterval(int interval);
    public void add(Session session);
    public Session createSession();
    public Session findSession(String id)throws IOException;
    public Session[] findSessions();
    //从持久化部件中获取session
    public void load() throws ClassNotFoundException,IOException;
    public void remove(Session session);
    //将session持久化
    public void unload() throws IOException;
}
