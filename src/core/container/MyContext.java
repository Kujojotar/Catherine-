package core.container;

import Annotation.WebFilter;
import Annotation.WebServlet;
import Constants.Constants;
import core.filter.Filter;
import core.loader.WebappLoader;
import core.servlet.Servlet;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import core.session.Session;
import core.session.StandardManager;
import core.util.Cookie;
import core.valve.Valve;
import core.valve.ValveContext;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.*;

public class MyContext implements Contained,Container {
    private Container parent;
    private Map<String,MyWrapper> wrappers;
    private Pipeline pipeline;
    private WebappLoader loader;
    private Map<String,String> urlServletMap;
    private StandardManager manager;

    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public WebappLoader getLoader() {
        return loader;
    }

    public void setLoader(WebappLoader loader) {
        this.loader = loader;
    }

    class MyContextBasicValve implements Valve{
        @Override
        public void invoke(ServletRequest request, ServletResponse response, ValveContext context) {
            Cookie sessionCookie= request.getCookie("JSESSIONID");

            if(sessionCookie==null){
                Session session=manager.createSession();
                response.getPrintWriter().println("Set-Cookie: JSESSIONID="+session.getId());
                request.setSession(session);
            }else{
                String jsessionId=sessionCookie.getValue();
                System.out.println(jsessionId);
                Session session= manager.findSession(jsessionId);
                if(session==null){
                    System.out.println("这个session过期了");
                }
            }
            response.getPrintWriter().println();
            String uri=request.getUri();
            String className=urlServletMap.get(uri);
            if(className==null){
                return ;
            }
            MyWrapper wrapper=wrappers.get(className);
            wrapper.invoke(request,response);
        }
    }

    public Map<String, MyWrapper> getWrappers() {
        return wrappers;
    }

    public Map<String, String> getUrlServletMap() {
        return urlServletMap;
    }

    public MyContext(){
        wrappers=new HashMap<>();
        pipeline=new SimplePipeline();
        pipeline.setBasic(new MyContextBasicValve());
        loader=new WebappLoader(this);
        urlServletMap=new HashMap<>();
        manager=new StandardManager();
        manager.setContainer(this);
    }

    /**
     * 从扫描包中获取注解信息
     * 只有了实现了Servlet接口并配置了@WebServlet的类才被视为有效的Servlet
     * 这个方法将配置的url与servlet类组成了映射
     */
    public void initWrapper(){
        try{
            File dir=new File(Constants.WEB_ROOT+File.separator+"source"+File.separator+"servlet");
            File[] files=dir.listFiles();
            Class clazz=null;
            String name=null;
            for(File file:files){
                name=file.getName().substring(0,file.getName().indexOf(".java"));
                clazz=loader.loadClass(file.getName().substring(0,file.getName().indexOf(".java")));
                if(clazz.getSuperclass()!=null&&!clazz.getSuperclass().isInstance(Servlet.class)){
                    continue;
                }
                if(clazz.isAnnotationPresent(WebServlet.class)){
                    Annotation annotation=clazz.getAnnotation(WebServlet.class);
                    String[] patterns=((WebServlet)annotation).urlPatterns();
                    ArrayList<String> urls=new ArrayList<>();
                    for(String url:patterns){
                        urls.add(url);
                    }
                    if(urls==null){
                        continue;
                    }else{
                        for(String url:urls){
                            urlServletMap.put(url, name);
                        }
                    }
                    MyWrapper wrapper=new MyWrapper(name);
                    wrappers.put(name, wrapper);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 扫描filter包
     * 只有实现了filter接口且配置了@WebFilter的类被视为有效的filter
     * 最后用一种看上去挺蠢的方式将filter添加到wrapper组件
     */
    public void addFilters() {
        File dir = new File(Constants.WEB_ROOT + File.separator + "source" + File.separator + "filter");
        File[] files = dir.listFiles();
        Class clazz = null;
        String name = null;
        try {
            for (File file : files) {
                name = file.getName().substring(0, file.getName().indexOf(".java"));
                clazz = Class.forName("source.filter."+name);
                if (clazz.getSuperclass()!=null&&clazz.getSuperclass().isInstance(Filter.class) && clazz.isAnnotationPresent(WebFilter.class)) {
                    Annotation annotation = clazz.getAnnotation(WebFilter.class);
                    String[] urls = ((WebFilter) annotation).urls();
                    for (String url : urls) {
                        String className = urlServletMap.get(url);
                        Filter filter;
                        if (className == null) {
                            continue;
                        } else {
                            MyWrapper wrapper = wrappers.get(className);
                            try {
                                filter = (Filter) clazz.newInstance();
                                wrapper.addFilter(filter);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public Container getContainer() {
        return parent;
    }

    @Override
    public void setContainer(Container container) {
        parent=container;
    }

    @Override
    public void invoke(ServletRequest request, ServletResponse response){
        pipeline.invoke(request,response);
    }

    public boolean isAddedWrapperOk(String fileName){
        try {
            Class clazz = loader.loadClass(fileName);
            if(clazz.getSuperclass()!=null&&clazz.getSuperclass().isInstance(Servlet.class)&& clazz.isAnnotationPresent(WebServlet.class)){
                String[] urls= ((WebServlet)clazz.getAnnotation(WebServlet.class)).urlPatterns();
                for(String url:urls){
                    urlServletMap.put(url, clazz.getName());
                    return true;
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void Notified(String fileName,char type) {
        switch (type){
            case'a':
                MyWrapper wrapper=new MyWrapper(fileName);
                if(isAddedWrapperOk(fileName)){
                    wrappers.put(fileName,wrapper);
                }
                break;
            case'u':
                MyWrapper wrapper1=wrappers.get(fileName);
                if(wrapper1==null){

                }else{
                    Servlet servlet=null;
                    try{
                        servlet=(Servlet) loader.loadClass(fileName).newInstance();
                        wrapper1.setInstance(servlet);
                    }catch(Exception e){
                        wrapper1.setInstance(null);
                        e.printStackTrace();
                    }
                }
                break;
            case'd':
                    MyWrapper wrapper2=wrappers.get(fileName);
                    if(wrapper2==null){
                    }else{
                        Set<Map.Entry<String,String>> sets=urlServletMap.entrySet();
                        Iterator<Map.Entry<String,String>> iterator= sets.iterator();
                        while(iterator.hasNext()){
                            Map.Entry entry=(Map.Entry) iterator.next();
                            String key=(String)entry.getKey();
                            String value=(String) entry.getValue();
                            if(value.equals(fileName)){
                                urlServletMap.remove(key);
                            }
                        }
                        wrappers.remove(wrapper2);
                    }
                break;
            default:
                break;
        }
    }
}
