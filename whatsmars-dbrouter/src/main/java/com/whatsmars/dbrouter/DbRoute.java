package com.whatsmars.dbrouter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DbRoute {
    String field() default "userId";
  
    String tableStyle() default "0000";
} 