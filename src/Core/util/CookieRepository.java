package Core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author james
 * Cookie缓存的模拟--实际的cookie好像是缓存在数据库中的
 * 这边主要模拟的是对过期cookie的删除
 */
public class CookieRepository implements Runnable {
    private  List<Cookie> cookies=new ArrayList<Cookie>();

    public CookieRepository()throws Exception{

    }

    public boolean addCookie(Cookie cookie){
        if(getCookieByKey(cookie.getKey())!=null){
            return false;
        }
        return cookies.add(cookie);
    }

    public Cookie getCookieByKey(String key){
        if(key==null){
            System.out.println("nmlgb");
            return null;
        }
        for(Cookie cookie:cookies){
            if(key.equals(cookie.getKey())){
                return cookie;
            }
        }
        return null;
    }

    public void removeCookieByKey(String key){
        if(key==null){
            System.out.println("nmlgb");
            return ;
        }
        int a=0;
        int b=cookies.size();
        Cookie cookie;
        while(a<b){
            cookie=cookies.get(a);
            if(key.equals(cookie.getKey())){
                cookies.remove(cookie);
                break;
            }
        }
    }

    /**
     * 开启一个检查线程
     */
    public void openThread(){
        Thread examineThread=new Thread(this);
        examineThread.run();
    }

    public void run(){
        while(true){
            int a=0;
            int b=cookies.size();
            Cookie cookie;
            while(a<b){
                cookie=cookies.get(a);
                if(cookie.isTimeOut(new Date())){
                    cookies.remove(cookie);
                    b--;
                }else{
                    a++;
                }
            }
            synchronized (this){
                try{
                    this.wait(300000);
                }catch(InterruptedException e){
                }
            }
        }
    }
}
