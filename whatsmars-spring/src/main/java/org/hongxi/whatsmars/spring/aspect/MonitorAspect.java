package org.hongxi.whatsmars.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2016/8/10.
 */
@Component
@Aspect
public class MonitorAspect {

    private static Logger logger = LoggerFactory.getLogger(MonitorAspect.class);

    @Around(
            value = "execution(* *(..)) && @annotation(monitor)",
            argNames = "pjp,monitor"
    )
    public Object doUmpLogging(ProceedingJoinPoint pjp, Monitor monitor) throws Throwable {
        String tag = monitor.tag();
        long start = System.currentTimeMillis();
        // record invocation (times)
        logger.info("{} start time: {}", tag, start);
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Exception e) {
            // record error
            logger.error("{} invoke error", tag, e);
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            // record time -> end - start
            logger.info("{} cost time: {}", tag, end - start);
        }
        return obj;
    }

}
