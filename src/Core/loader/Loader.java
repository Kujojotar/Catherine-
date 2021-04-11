package Core.loader;

import Core.container.Container;

/**
 * @author james
 * @version 0.1
 * 类加载器接口的简单定义
 */
public interface Loader {
    public Container getContainer();
    public void setContainer(Container container);
    public boolean getDelegate();
    public void setDelegate(boolean delegate);
    public boolean getReloadable();
    public boolean modified();
}
