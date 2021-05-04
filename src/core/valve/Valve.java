package core.valve;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;

public interface Valve {
    public void invoke(ServletRequest request, ServletResponse response,ValveContext context);
}
