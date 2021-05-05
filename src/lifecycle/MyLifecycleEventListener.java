package lifecycle;

import logger.MyEventLogger;

public class MyLifecycleEventListener implements LifecycleEventListener {
    public MyLifecycleEventListener(){}

    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        MyEventLogger.log(event);
    }
}
