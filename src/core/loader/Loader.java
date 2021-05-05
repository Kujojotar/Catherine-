package core.loader;

import core.container.Container;

/**
 * @author james
 * @version 0.1
 * 类加载器接口的简单定义
 */
public interface Loader {
    /**
     * 获取类加载器关联的容器
     * @return container
     */
    public Container getContainer();

    /**
     * 设置类加载器关联的容器
     * @param container
     */
    public void setContainer(Container container);

    /**
     * 暂时还不知道这是什么玩意
     * @return boolean
     */
    public boolean getDelegate();

    /**
     * 暂时还不知道这是什么玩意
     * @param delegate
     */
    public void setDelegate(boolean delegate);

    /**
     * 获取类加载器是否支持重加载，默认不支持
     * @return
     */
    public boolean getReloadable();

    /**
     * 获取类加载器监控下的目录是否发生了变化
     * @return
     */
    public boolean modified();
}
