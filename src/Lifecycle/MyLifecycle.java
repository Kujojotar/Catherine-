package Lifecycle;

/**
 * 一个巨简单的生命周期接口
 * 这边只需要实现启动与关闭
 */
public interface MyLifecycle {
    void start();
    void shutdown();
}
