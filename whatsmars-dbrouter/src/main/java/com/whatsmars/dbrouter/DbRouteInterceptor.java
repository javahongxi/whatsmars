package com.whatsmars.dbrouter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DbRouteInterceptor {
    private DbRouter dbRouter;
  
    public DbRouteInterceptor() {}
  
    @Pointcut("@annotation(com.whatsmars.dbrouter.DbRoute)")
    public void aopPoint() {}
  
    @Before("aopPoint()")
    public Object doRoute(JoinPoint jp) throws Throwable {
        boolean result = true;
        Method method = this.getMethod(jp);
        DbRoute dbRoute = method.getAnnotation(DbRoute.class);
        String routeField = dbRoute.field();  // userId
        Object[] args = jp.getArgs();  
        if(args != null && args.length > 0) {  
            for(int i = 0; i < args.length; ++i) {  
                String routeFieldValue = BeanUtils.getProperty(args[i], routeField);
                if(StringUtils.isNotEmpty(routeFieldValue)) {
                    if("userId".equals(routeField)) {  
                        this.dbRouter.route(routeField);
                    }
                    break;  
                }  
            }  
        }  
  
        return Boolean.valueOf(result);
    }  
  
    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {  
        Signature sig = jp.getSignature();
        MethodSignature msig = (MethodSignature)sig;
        return this.getClass(jp).getMethod(msig.getName(), msig.getParameterTypes());  
    }  
  
    private Class<? extends Object> getClass(JoinPoint jp) throws NoSuchMethodException {  
        return jp.getTarget().getClass();  
    }

    public DbRouter getDbRouter() {
        return dbRouter;
    }

    public void setDbRouter(DbRouter dbRouter) {
        this.dbRouter = dbRouter;
    }
}