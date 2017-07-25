package com.itlong.whatsmars.spring.boot.controller;

import com.itlong.whatsmars.spring.boot.config.UserConfig;
import com.itlong.whatsmars.spring.boot.common.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * Created by shenhongxi on 2017/3/27.
 */
@Controller
public class SampleController {

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private LocaleService localeService;

    /**
     * 设置区域解析器 (default is AcceptHeaderLocaleResolver)
     */
    @Bean
    public LocaleResolver localeResolver() {
        AbstractLocaleResolver localeResolver = new SessionLocaleResolver();
        //localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @RequestMapping("/changeLang")
    @ResponseBody
    public String changeLang(HttpServletRequest request, HttpServletResponse response, String lang){
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if ("zh".equals(lang)) {
            // request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("zh", "CN"));
            localeResolver.setLocale(request, response, new Locale("zh", "CN"));
        } else if("en".equals(lang)){
            // request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("en", "US"));
            localeResolver.setLocale(request, response, new Locale("en", "US"));
        }
        return "lang:" + lang;
    }

    @RequestMapping("/")
    public String home(Map<String,Object> map) {
        System.out.println(localeService.isEnLocale());
        map.put("hello", "Hi, boy!");
        map.put("country", localeService.getMessage("country"));
        return "index";
    }

    @RequestMapping("/do")
    @ResponseBody
    public String motan() {
        return userConfig.getWelcome();
    }

}
