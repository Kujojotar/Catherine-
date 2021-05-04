package core.filter;

import java.util.Enumeration;

public interface FilterConfig {
    public String getFilterName();
    public String getInitParameter(String name);
    //那个Enumeration好像是个很老的东西了，这边用List应该也可以达到差不多的效果
    public Enumeration<String> getInitParameterNames();
}
