package com.whatsmars.earth.web.interceptor;

import com.whatsmars.earth.domain.enums.UserTypeEnum;
import com.whatsmars.earth.domain.misc.LoginContextHolder;
import com.whatsmars.earth.domain.permission.Permission;
import com.whatsmars.earth.domain.pojo.User;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Author: qing
 * Date: 14-10-29
 * 检查用户权限
 */
public class PermissionInterceptor implements HandlerInterceptor {

    protected static final Logger logger = Logger.getLogger(PermissionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;

        //
        Method method = handlerMethod.getMethod();

        User user = LoginContextHolder.getLoginUser();//用户必须首先登陆
        Permission annotation = (Permission)method.getAnnotation(Permission.class);

        String errorUrl = "/error.jhtml?message=Permission denied";
        //如果方法上有@Permission注释，则优先判定
        if(annotation != null) {
            boolean available = this.validate(annotation,user);
            if(!available) {
                //如果用户的权限中没有包含允许的操作，则跳转到提示页
                response.sendRedirect(errorUrl);
            }
            return available;
        }

        //如果方法上没有注释，那么我们再次检测class上是否有@Permission注释
        Class clazz = handlerMethod.getBeanType();
        annotation = (Permission)clazz.getAnnotation(Permission.class);
        if(annotation != null) {
            boolean available = this.validate(annotation,user);
            if(!available) {
                //如果用户的权限中没有包含允许的操作，则跳转到提示页
                response.sendRedirect(errorUrl);
            }
            return available;
        }
        return true;

    }

    private boolean validate(Permission annotation,User user) throws Exception{
        UserTypeEnum[] roles = annotation.roles();
        if(roles.length == 0) {
            return true;
        }
        boolean hasRole = false;//是否包含指定的角色
        for(UserTypeEnum e : roles) {
            if(e.code == user.getType()) {
                hasRole = true;
            }
        }

        if(hasRole == false) {
            return false;
        }

        String [] operations = annotation.operations();
        //如果没有指定operation，则认为当前role下的所有操作都被允许
        if(operations == null || operations.length == 0) {
            return true;
        }
        Set<String> userOperations = user.getOperations();
        if(CollectionUtils.isEmpty(userOperations)) {
            return false;
        }
        for(String item : operations) {
            if(userOperations.contains(item)) {
                //如果用户包含这个操作，则返回true，继续执行
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    protected User decoder(String content){
        if(StringUtils.isBlank(content)){
            return null;
        }
        JSONObject json = JSONObject.fromObject(content);
        if(json.isNullObject()){
            return null;
        }
        User user = new User();
        user.setId(json.getLong("id"));

        if(json.has("name")) {
            user.setUsername(json.getString("name"));
        }
        user.setType(json.getInt("type"));
        return (User)JSONObject.toBean(json,User.class);
    }

}