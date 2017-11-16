package com.itlong.whatsmars.spring.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenhongxi on 2016/8/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Monitor {
    String DEFAULT_TAG_NAME = "@@USE_METHOD_NAME";

    String tag() default "@@USE_METHOD_NAME";

    String message() default "";

    boolean heart() default false;

}
