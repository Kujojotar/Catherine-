package Annotation;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 对注解的处理使用了reflections框架
 * 专门用于扫描并处理注解的类
 * 感觉用起来比文件操作更优雅
 */
public class WebServletProcessor {
    private Reflections reflections=null;
    private Map<String,String> urlRecords;

    public WebServletProcessor(){
        reflections=new Reflections(new ConfigurationBuilder()
                .forPackages("source.servlet")
                .addScanners(new TypeAnnotationsScanner())
        );
        urlRecords=new HashMap<>();
    }

    /**
     * 大致的扫描过程
     */
    public void initRecord(){
        Set<Class<?>> set=reflections.getTypesAnnotatedWith(WebServlet.class);
        set.forEach(a->{
            // TODO:更加详细的注解处理流程 2021/4/19
            String servletName=a.getName();
            Annotation annotation= a.getAnnotation(WebServlet.class);
            String[] urls=((WebServlet)annotation).urlPatterns();
            for(String url:urls){
                urlRecords.put(url,servletName);
            }
        });
    }

    public Map<String,String> getUrlRecords(){
        return urlRecords;
    }


}

class t{
    public static void main(String[] args) {
        WebServletProcessor processor=new WebServletProcessor();
        processor.initRecord();
        System.out.println(processor.getUrlRecords());
    }
}