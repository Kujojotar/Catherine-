package Core.container;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;
import Core.valve.Valve;

import java.util.HashMap;
import java.util.Map;

/**
 * @author james
 * 简单的engine容器
 */
public class Engine implements Container{
    private Map<String,Host> map;
    private Host defaultHost;
    private Valve basicValve;

    public Valve getBasicValve() {
        return basicValve;
    }

    public void setBasicValve(Valve basicValve) {
        this.basicValve = basicValve;
    }

    public Engine(){
        map=new HashMap<>();
        defaultHost=new Host();
    }

    public void addHost(String path,Host host){
        map.put(path,host);
    }


    //设计出现了一些失误，不应该偷懒在Container接口里设置这个方法👻
    @Override
    public void Notified(String fileName, char type) {
        //do nothing
    }

    @Override
    public void invoke(ServletRequest request, ServletResponse response) {
        defaultHost.invoke(request, response);
    }
}
