package Lifecycle;

import event.Event;

import java.util.EventObject;

/**
 * 一个简单的事件信息储存对象
 * 原版里面构造器中需要lifecycle，感觉有些奇怪
 */
public class LifecycleEvent extends EventObject implements Event {
    private static final long serialVersionUID = 7099057708183571937L;
    private final long timestamp= System.currentTimeMillis();
    private String message;

    public LifecycleEvent(Object source,String message){
        super(source);
        this.message=message;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    public String getMessage(){
        return message;
    }
}
