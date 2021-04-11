package Core.valve;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;

public interface ValveContext {
    public void invokeNext(ServletRequest request, ServletResponse response);
}
