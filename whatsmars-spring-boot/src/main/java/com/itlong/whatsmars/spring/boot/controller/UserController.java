package com.itlong.whatsmars.spring.boot.controller;

import com.itlong.whatsmars.spring.boot.config.UserConfig;
import com.itlong.whatsmars.spring.boot.util.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * Created by shenhongxi on 2017/3/27.
 */
@Controller
public class UserController {

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private LocaleUtils localeUtils;

    /**
     * 设置区域解析器 (default is AcceptHeaderLocaleResolver)
     */
    @Bean
    public LocaleResolver localeResolver() {
        AbstractLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;
    }

    @RequestMapping("/changeLang")
    @ResponseBody
    public String changeLang(HttpServletRequest request, String lang){
        if ("zh".equals(lang)) {
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh", "CN"));
        } else if("en".equals(lang)){
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US"));
        }
        return "lang:" + lang;
    }

    @RequestMapping("/")
    public String home(Map<String,Object> map) {
        map.put("hello", "Hi, boy!");
        map.put("country", localeUtils.getMessage("country"));
        return "index";
    }

    @RequestMapping("/do")
    @ResponseBody
    public String doSth() {
        return userConfig.getWelcome();
    }

}
