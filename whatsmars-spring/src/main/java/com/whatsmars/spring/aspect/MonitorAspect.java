package com.whatsmars.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by shenhongxi on 2016/8/10.
 */
@Aspect
public class MonitorAspect {

    private String tagPrefix;

    @Around(
            value = "execution(* *(..)) && @annotation(monitor)",
            argNames = "pjp,monitor"
    )
    public Object doUmpLogging(ProceedingJoinPoint pjp, Monitor monitor) throws Throwable {
        // String tag = monitor.tag();
        // boolean heart = monitor.heart();
        long start = System.currentTimeMillis();
        // record invocation (times)
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Exception e) {
            // record error
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            // record time -> end - start
        }
        return obj;
    }

    public String getTagPrefix() {
        return tagPrefix;
    }

    public void setTagPrefix(String tagPrefix) {
        this.tagPrefix = tagPrefix;
    }
}
