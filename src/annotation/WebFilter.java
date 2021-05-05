package annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebFilter {
    String[] urls() default {};
    String description() default "";
    String filterName() default "";
    boolean asyncSupported() default false;
    WebInitParam[] initParams() default {};
}
