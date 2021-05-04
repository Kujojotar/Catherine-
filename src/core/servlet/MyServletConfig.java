package core.servlet;

import javax.naming.Context;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MyServletConfig implements ServletConfig{
    public String servletName;
    public Map<String,String> initParameters;

    public MyServletConfig(){
        initParameters=new HashMap<>();
    }

    public void add(String key,String value){
        initParameters.put(key,value);
    }

    @Override
    public String getServletName() {
        return servletName;
    }

    @Override
    public Context getServletContext() {
        return null;
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
