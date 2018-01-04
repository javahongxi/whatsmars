package org.hongxi.whatsmars.spring.aspect;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented 
public @interface SystemLog {

	String description() default "";
	int logType();
	
}
