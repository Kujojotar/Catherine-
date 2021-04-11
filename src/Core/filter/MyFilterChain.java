package Core.filter;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;

import java.util.ArrayList;

/**
 * @author james
 * 类似管道方法定义的一个简单的过滤器链
 */
public class MyFilterChain implements FilterChain{
    private ArrayList<Filter> filters=new ArrayList<>();
    private Filter exeFilter;
    private int i=0;

    public synchronized void doFilter(ServletRequest request, ServletResponse response){
        if(i< filters.size()){
            exeFilter=filters.get(i);
            i++;
        }else if(i== filters.size()){
            i=0;
            return ;
        }else{
            System.out.println("出了些幺蛾子");
            return ;
        }
        try {
            exeFilter.doFilter(request, response, this);
        }catch (Exception e){
        }
    }

    public void addFilter(Filter filter){
        filters.add(filter);
    }

    public void addFilterIfNoPrevious(Filter filter){
        if(filters.contains(filter)){
            return ;
        }
        filters.add(filter);
    }

    public void removeFilter(Filter filter){
        filters.remove(filter);
    }
}
