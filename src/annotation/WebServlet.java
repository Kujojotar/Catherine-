package annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServlet {
    String name() default "";
    String[] urlPatterns() default {};
    int loadOnStartup() default -1;
    boolean asyncSupported() default false;
    String description() default "";
    String displayName() default "";
    WebInitParam[] initParams() default {};
}
