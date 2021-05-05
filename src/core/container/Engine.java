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
 * 简单的engine容器
 */
public class Engine implements Container, MyLifecycle {
    private Map<String,Host> map;
    private Host defaultHost;
    private Valve basicValve;
    private Pipeline pipeline;

    class MyEngineBasicValve implements Valve{
        @Override
        public void invoke(ServletRequest request, ServletResponse response, ValveContext context) {
            defaultHost.invoke(request,response);
        }
    }

    public Valve getBasicValve() {
        return basicValve;
    }

    public void setBasicValve(Valve basicValve) {
        this.basicValve = basicValve;
    }

    public Engine(){
        map=new HashMap<>();
        defaultHost=new Host();
        pipeline=new SimplePipeline();
        basicValve=new MyEngineBasicValve();
        init();
    }

    private void init(){
        pipeline.setBasic(basicValve);
    }

    @Override
    public void start(){
    }

    @Override
    public void shutdown(){

    }

    public void addHost(String path,Host host){
        map.put(path,host);
    }


    @Override
    public void invoke(ServletRequest request, ServletResponse response) {
        defaultHost.invoke(request, response);
    }
}
