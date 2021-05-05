package logger;

import event.Event;

/**
 * 一个很傻逼的记录器
 */
public class MyEventLogger {

    /**
     * 👻
     * @throws Exception
     */
    public MyEventLogger()throws Exception{
        throw new Exception("不能被实例化");
    }

    public static void log(Event event){
        System.out.println(event.getMessage());
    }

}
