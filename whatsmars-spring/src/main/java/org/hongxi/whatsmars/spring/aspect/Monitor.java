package org.hongxi.whatsmars.spring.aspect;

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

    /**
     * 标识每一个方法，全局唯一
     */
    String tag();

}
