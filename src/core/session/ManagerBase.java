package core.session;

import java.util.HashMap;
import java.util.Map;

public abstract class ManagerBase implements Manager {
    protected Map<String,Session> sessions=new HashMap<>();

    public void add(Session session){
        synchronized (sessions){
            sessions.put(session.getId(),session);
        }
    }

    public void remove(Session session){
        synchronized (sessions){
            sessions.remove(session.getId());
        }
    }

    public Session[] findSessions(){
        Session results[]=null;
        synchronized (sessions){
            results=new Session[sessions.size()];
            results=(Session[]) sessions.values().toArray(results);
        }
        return results;
    }

    public Session findSession(String id){
        if(id==null){
            return null;
        }
        synchronized (sessions){
            Session session=sessions.get(id);
            return session;
        }
    }
}
