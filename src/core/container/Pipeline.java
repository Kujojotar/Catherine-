package core.container;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;
import core.valve.Valve;

public interface Pipeline {
    public Valve getBasic();
    public void setBasic(Valve valve);
    public void addValve(Valve valve);
    public Valve[] getValves();
    public void invoke(ServletRequest request, ServletResponse response);
    public void removeValve(Valve valve);
}
