package org.hongxi.whatsmars.spring.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class SystemLogAspect {

	private static Logger logger = Logger.getLogger(SystemLog.class);

	// Controller层切点
	@Pointcut("@annotation(org.hongxi.whatsmars.spring.aspect.SystemLog)")
	public void controllerAspect() {
	}

	// 操作记录
	@After("controllerAspect()")
	public void doAfter(JoinPoint joinPoint) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// 读取session中的用户
//		User user = SessionUtils.getUserFromSession(request);
		
//		if(null == user){
//			logger.info("未登录日志不记录");
//			return;
//		}
		
		try {
			Object[] args = getLogMethodDescription(joinPoint);
			// *========数据库日志=========*//
//			logService.saveLog(user.getId(), user.getAccount(), Integer.valueOf(args[1].toString()),
//                    IpUtils.getIpAddr(request), args[0].toString(),
//                    user.getManufacturerId(), reqDescription(request));

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	// 异常记录
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "exception")
    public void doException(JoinPoint joinPoint, Exception exception) {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// 读取session中的用户
//		User user = SessionUtils.getUserFromSession(request);
		
//		if(null == user){
//			logger.info("未登录日志不记录");
//			return;
//		}
		
		try {
			Object[] args = getLogMethodDescription(joinPoint);

			// *========数据库日志=========*//
//			logService.saveLog(user.getId(), user.getAccount(), LogType.EXCEPTION_LOG,
//					IpUtils.getIpAddr(request), args[0].toString() + "-->" + exception.getMessage(),
//					user.getManufacturerId(), reqDescription(request));

		} catch (Exception e) {
			// 记录本地异常日志
			logger.error(e);
			e.printStackTrace();
		}  
    }

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解 切点
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] getLogMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class<?> targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		Object[] result = new Object[2];
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					result[0] = method.getAnnotation(SystemLog.class).description();
					result[1] = method.getAnnotation(SystemLog.class).logType();
					break;
				}
			}
		}
		return result;
	}
}
