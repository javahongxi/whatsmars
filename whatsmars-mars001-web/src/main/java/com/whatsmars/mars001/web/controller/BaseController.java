package com.whatsmars.mars001.web.controller;

import com.whatsmars.mars001.common.util.DESUtils;
import com.whatsmars.mars001.domain.constants.Constants;
import com.whatsmars.mars001.domain.misc.SystemConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuguanqing on 15/4/10.
 */
public class BaseController {

    @Autowired(required = false)
    protected SystemConfig systemConfig;

    protected static final String TOKEN_KEY = "_lender_token_";

    /**
     * 创建CXRF Token，避免表单重复提交或者跨站操作
     * @param response 生成token，path为当前页面的路径，比如：/teacher
     * @return
     */
    public String createToken(HttpServletRequest request,HttpServletResponse response) {
        String token = RandomStringUtils.random(32,true,true);

        Cookie cookie = new Cookie(TOKEN_KEY,DESUtils.encrypt(token,Constants.HTTP_ENCRYPT_KEY));
        cookie.setDomain(systemConfig.getCookieDomain());
        cookie.setPath(this.getRequestPath(request));
        //cookie.setMaxAge(TOKEN_MAX_AGE);
        response.addCookie(cookie);
        return token;
    }


    /**
     * 当表单提交，且后台处理成功后，移除Token
     * @param response
     * @return
     */
    public void removeToken(HttpServletRequest request,HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN_KEY,null);
        cookie.setDomain(systemConfig.getCookieDomain());
        cookie.setMaxAge(0);//立即过期
        cookie.setPath(this.getRequestPath(request));
        response.addCookie(cookie);
    }

    /**
     * 从cookie中获取token的值
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return null;
        }

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(TOKEN_KEY)) {
                return DESUtils.decrypt(cookie.getValue(), Constants.HTTP_ENCRYPT_KEY);
            }
        }
        return null;
    }


    /**
     * 获取客户端的实际IP
     * @param request
     * @return
     */
    public String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");

        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 从cookie中获取当期path下的验证码信息
     * @param request
     * @return
     */
    public String getCurrentValidateCode(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(Constants.VALIDATE_CODE_COOKIE_KEY)) {
                String source = cookie.getValue();
                return DESUtils.decrypt(source,Constants.HTTP_ENCRYPT_KEY);
            }
        }
        return null;
    }

    /**
     * 获取请求的path路径：比如“/user/test.jhtml”返回“/user”.
     * @param request
     * @return
     */
    protected String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        int index = servletPath.lastIndexOf("/");
        if(index == 0) {
            return "/";
        }
        return servletPath.substring(0,index);
    }
}
