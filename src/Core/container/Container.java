package Core.container;

import Core.servlet.ServletRequest;
import Core.servlet.ServletResponse;

public interface Container {
    public void Notified(String fileName,char type);
    public void invoke(ServletRequest request, ServletResponse response);
}
