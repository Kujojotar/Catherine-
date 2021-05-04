package core.container;


import core.filter.Filter;
import core.filter.MyFilterChain;
import core.loader.WebappLoader;
import core.servlet.Servlet;
import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import core.valve.Valve;
import core.valve.ValveContext;
import Lifecycle.MyLifecycle;

/**
 * @author james
 * 最小容器Wrapper的实现
 */
public class MyWrapper implements Container,Contained, MyLifecycle {
    //wrapper没有更小级别的子容器了
    private final Container container=null;
    //wrapper的上级容器，一般是Context
    private Container parent;
    //wrapper管理的servlet的实例
    private Servlet instance;
    //wrapper包含的类加载器，负责载入wrapper所对应的servlet类
    private WebappLoader loader;
    //要加载的servlet的；类名
    private String className;
    //wrapper容器的管道
    private Pipeline pipeline;
    //过滤器链
    private MyFilterChain chain;

    public MyWrapper(String className){
        this.className=className;
        loader=new WebappLoader(this);
        pipeline=new SimplePipeline();
        pipeline.setBasic(new MyWrapperBasicValve());
        chain=new MyFilterChain();
        start();
    }

    class MyWrapperBasicValve implements Valve {
        @Override
        public void invoke(ServletRequest request, ServletResponse response, ValveContext context) {
            chain.doFilter(request, response);
            instance.service(request,response);
            response.getPrintWriter().flush();
        }
    }

    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public void setInstance(Servlet instance) {
        this.instance = instance;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {
    }

    @Override
    public void start() {
        Class clazz=null;
        try{
            clazz=loader.loadClass(className);
        }catch (ClassNotFoundException e){
            System.out.println("提供了一个无效的Servlet类名");
        }
        try {
            instance = (Servlet) clazz.newInstance();
        }catch (Exception e){
            System.out.println("初始化的过程似乎出了些问题");
            instance=null;
        }
    }

    @Override
    public void shutdown() {
       parent=null;
       instance=null;
       className=null;
       loader.shutdown();
       loader=null;
    }

    public void addFilter(Filter filter){
        chain.addFilterIfNoPrevious(filter);
    }

    @Override
    public void invoke(ServletRequest request,ServletResponse response){
        pipeline.invoke(request,response);
    }
}
