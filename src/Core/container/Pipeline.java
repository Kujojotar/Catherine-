package Core.container;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;
import Core.valve.Valve;

public interface Pipeline {
    public Valve getBasic();
    public void setBasic(Valve valve);
    public void addValve(Valve valve);
    public Valve[] getValves();
    public void invoke(ServletRequest request, ServletResponse response);
    public void removeValve(Valve valve);
}
