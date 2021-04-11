package Core.valve;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;

public interface Valve {
    public void invoke(ServletRequest request, ServletResponse response,ValveContext context);
}
