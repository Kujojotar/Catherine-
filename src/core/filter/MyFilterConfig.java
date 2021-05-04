package core.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MyFilterConfig implements FilterConfig {
    private String filterName;
    private Map<String,String> initParameters;

    public MyFilterConfig(){
        initParameters=new HashMap<>();
    }

    public void add(String key,String value){
        initParameters.put(key,value);
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    @Override
    public String getFilterName() {
        return filterName;
    }

    @Override
    public String getInitParameter(String name) {
        return initParameters.get(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return null;
    }
}
