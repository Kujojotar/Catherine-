package Core.session;

public interface Session {

    public long getCreationTime();
    public void setCreationTime(long time);

    public String getId();
    public void setId(String id);

    public Manager getManager();
    public void setManager(Manager manager);

    //设置session的最长存活时间(从最后一次被访问到多久后失效)
    public void setMaxInactiveInterval(int interval);

    public int getMaxInactiveInterval();
    //作为Session的包装类，提高安全
    public HttpSession getSession();

    public void setValid(boolean isValid);
    public boolean isValid();
    //session失效时调用和的方法
    public void expire();

    public void addSessionListener(SessionListener listener);

    public void removeSessionListener(SessionListener listener);

    public void recycle();

    public void setAttribute(String key,Object val);

    public Object getAttribute(String key);

    public long getAccessedTime();
}
