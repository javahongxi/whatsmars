package org.hongxi.whatsmars.earth.web.interceptor;

import org.hongxi.whatsmars.earth.common.util.DESUtils;
import org.hongxi.whatsmars.earth.domain.misc.LoginContext;
import org.hongxi.whatsmars.earth.domain.misc.LoginContextHolder;
import org.hongxi.whatsmars.earth.domain.misc.SystemConfig;
import org.hongxi.whatsmars.earth.domain.pojo.User;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Author: qing
 * Date: 14-10-29
 * 用于控制接口是否为需要“登陆”，如果需要登陆，则检测登陆信息是否完备。
 */
public class LoginInterceptor implements HandlerInterceptor{

    private SystemConfig systemConfig;

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return true;
        }
        //解析cookie
        String cookieKey = systemConfig.getCookieKey();
        for(Cookie cookie : cookies) {
            String key = cookie.getName();
            String content = cookie.getValue();
            if(key.equalsIgnoreCase(cookieKey)) {
               if(StringUtils.isNotBlank(content)) {
                   String source = DESUtils.decrypt(content, systemConfig.getCookieSecurityKey());
                   User user = this.decoder(source);
                   LoginContext context = new LoginContext();
                   context.setUser(user);
                   LoginContextHolder.set(context);
               }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null) {
            User user = LoginContextHolder.getLoginUser();
            modelAndView.addObject("loginUser", user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginContextHolder.clear();
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

        user.setUsername(json.getString("name"));
        user.setType(json.getInt("type"));
        if(json.has("phone")){
            user.setPhone(json.getString("phone"));
        }
        if(json.has("loginTime")) {
            user.setLoginTime(new Date(json.getLong("loginTime")));
        }
        return user;
    }


}
