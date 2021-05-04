package core.container;

import core.servlet.ServletRequest;
import core.servlet.ServletResponse;

public interface Container {

    public void invoke(ServletRequest request, ServletResponse response);
}
