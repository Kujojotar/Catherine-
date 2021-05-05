package lifecycle;

/**
 * 一个巨简单的生命周期接口
 * 这边只需要实现启动与关闭
 */
public interface MyLifecycle {
    public static final String START_EVENT="start";
    public static final String BEFORE_START_EVENT="before_start";
    public static final String AFTER_START_EVENT="after_start";
    public static final String STOP_EVENT="stop";
    public static final String BEFORE_STOP_EVENT="before_stop";
    public static final String AFTER_STOP_EVENT="after_stop";
    void start();
    void shutdown();
}
