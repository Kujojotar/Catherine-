package core.valve;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;

public interface ValveContext {
    public void invokeNext(ServletRequest request, ServletResponse response);
}
