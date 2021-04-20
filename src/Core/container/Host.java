package Core.container;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;
import Core.valve.Valve;

import java.util.HashMap;
import java.util.Map;

/**
 * @author james
 * 看了看好像还有热部署什么的
 * 暂且先不做的那么复杂
 */
public class Host implements Container {
    private Map<String,MyContext> map;
    private MyContext defaultContext;
    private Valve basicValve;

    public Valve getBasicValve() {
        return basicValve;
    }

    public void setBasicValve(Valve basicValve) {
        this.basicValve = basicValve;
    }

    public Host(){
        map=new HashMap<>();
        defaultContext=new MyContext();
    }

    public void addContext(String path,MyContext context){
        map.put(path, context);
    }

    //设计出现了一些失误，不应该偷懒在Container接口里设置这个方法👻
    @Override
    public void Notified(String fileName, char type) {
        //doNothing
    }

    @Override
    public void invoke(ServletRequest request, ServletResponse response) {
        defaultContext.invoke(request,response);
    }
}
