package core.container;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import core.valve.Valve;
import core.valve.ValveContext;

import java.util.List;
import java.util.ArrayList;

/**
 * @author james
 * @version 0.1
 * 一个简单的管道实现类
 */
public class SimplePipeline implements Pipeline {
    //个人怀疑书上是因为年代过于久远的原因采取了一些觉得不太对劲的实现方式，在这里我直接用了ArrayList
    private List<Valve> valves;
    //基础阀
    private Valve basicValve;
    //帮助实现管道任务的一个对象
    private SimpleValveContext context;

    public SimplePipeline(){
        valves=new ArrayList<>();
        context=new SimpleValveContext();
    }

    class SimpleValveContext implements ValveContext{
        private int s=0;
        private Valve exeValve;

        @Override
        public void invokeNext(ServletRequest request, ServletResponse response) {
             if(s< valves.size()){
                 exeValve=valves.get(s);
                 s++;
                 exeValve.invoke(request,response,this);
             }else if(s==valves.size()){
                 s=0;
                 basicValve.invoke(request,response,this);
             }else{
                 System.out.println("出错了");
             }
        }
    }

    @Override
    public Valve getBasic() {
        return basicValve;
    }

    @Override
    public void setBasic(Valve valve) {
        basicValve=valve;
    }

    @Override
    public void addValve(Valve valve) {
        valves.add(valve);
    }

    @Override
    public Valve[] getValves() {
        return (Valve[])valves.toArray();
    }

    @Override
    public void invoke(ServletRequest request, ServletResponse response) {
        context.invokeNext(request,response);
    }

    @Override
    public void removeValve(Valve valve) {
        valves.remove(valve);
    }
}
