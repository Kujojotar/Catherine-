package core.container;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import core.valve.Valve;
import core.valve.ValveContext;
import lifecycle.MyLifecycle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author james
 * 看了看好像还有热部署什么的
 * 暂且先不做的那么复杂
 */
public class Host implements Container, MyLifecycle {
    private Map<String,MyContext> map;
    private MyContext defaultContext;
    private Valve basicValve;
    private Pipeline pipeline;

    class MyHostBasicValve implements Valve{
        @Override
        public void invoke(ServletRequest request, ServletResponse response, ValveContext context) {
            defaultContext.invoke(request,response);
        }
    }

    public Valve getBasicValve() {
        return basicValve;
    }

    public void setBasicValve(Valve basicValve) {
        this.basicValve = basicValve;
    }

    public Host(){
        map=new HashMap<>();
        defaultContext=new MyContext();
        pipeline=new SimplePipeline();
        basicValve=new MyHostBasicValve();
        init();
    }

    private void init(){
        pipeline.setBasic(basicValve);
        defaultContext.initWrapper();
        defaultContext.addFilters();
    }

    @Override
    public void start(){

    }

    @Override
    public void shutdown(){

    }

    public void addContext(String path,MyContext context){
        map.put(path, context);
    }


    @Override
    public void invoke(ServletRequest request, ServletResponse response) {
        defaultContext.invoke(request,response);
    }
}
