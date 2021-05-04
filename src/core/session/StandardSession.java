package core.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author james
 * Session的简单实现类
 */
public class StandardSession implements Session, Serializable {
    private Map<String,Object> attributes;
    //Session的管理类
    private Manager manager;
    //传说中的那个jsessionid
    private String id;
    //Session创建时间，这边设置的是现在的毫秒数
    private long creationTime=0;

    private boolean isValid=false;
    //Session过期时间，这边设置为10分钟
    private int maxInactiveInterval=600000;
    //最后一次被获取的时间
    private long accessedTime=creationTime;
    //记录是否过期
    private boolean expired=false;

    public StandardSession(){
        attributes=new HashMap<>();
    }

    public StandardSession(Manager manager){
        this.manager=manager;
        creationTime=System.currentTimeMillis();
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public void setCreationTime(long time) {
        this.creationTime=time;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }

    @Override
    public Manager getManager() {
        return manager;
    }

    @Override
    public void setManager(Manager manager) {
        this.manager=manager;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
         maxInactiveInterval=interval;
    }

    @Override
    public int getMaxInactiveInterval(){
        return maxInactiveInterval;
    }

    @Override
    public HttpSession getSession() {
        accessedTime=System.currentTimeMillis();
        return new FacadeHttpSession(this);
    }

    @Override
    public void setValid(boolean isValid) {
         this.isValid=isValid;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void expire() {
        manager.remove(this);
        expired=true;
    }

    @Override
    public void addSessionListener(SessionListener listener) {

    }

    @Override
    public void removeSessionListener(SessionListener listener) {

    }

    @Override
    public void recycle() {

    }

    @Override
    public void setAttribute(String key, Object val) {
        accessedTime=System.currentTimeMillis();
        attributes.put(key,val);
    }

    @Override
    public Object getAttribute(String key) {
        accessedTime=System.currentTimeMillis();
        return attributes.get(key);
    }

    public long getAccessedTime(){
        return  accessedTime;
    }


}
