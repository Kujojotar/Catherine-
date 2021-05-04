package core.server;

import core.connector.HttpConnector;
import core.container.Engine;
import Lifecycle.MyLifecycle;

/**
 * 一个简单的server抽象层实现类
 */
public class MyServer implements Server, MyLifecycle {
    private HttpConnector connector;
    private Engine engine;

    public MyServer(){
        engine = new Engine();
        connector=new HttpConnector(engine);
    }

    @Override
    public void start() {
        connector.start();
        Thread thread=new Thread(connector);
        thread.start();
    }

    @Override
    public void shutdown() {

    }
}
