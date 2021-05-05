package constants;

import java.util.HashMap;
import java.util.Map;

public final class HttpStatusWithMessage {
    private static Map<Integer,String> map=new HashMap<>();

    static{
        map.put(200,"OK");
        map.put(302,"Moved Temporarily");
        map.put(404,"Not Found");
    }

    public HttpStatusWithMessage() throws Exception{
        throw new Exception("这个类不能被实例化");
    }

    public static String getMessage(Integer code){
        return map.get(code);
    }
}
