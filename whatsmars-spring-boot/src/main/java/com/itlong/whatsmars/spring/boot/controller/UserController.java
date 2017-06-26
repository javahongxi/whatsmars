package com.itlong.whatsmars.spring.boot.controller;

import com.itlong.whatsmars.spring.boot.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

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
    private MessageSource messageSource;

    /**
     * 设置区域解析器 (default is AcceptHeaderLocaleResolver)
     */
    @Bean
    public LocaleResolver localeResolver() {
        FixedLocaleResolver localeResolver = new FixedLocaleResolver ();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;

    }

    @RequestMapping("/")
    public String home(Map<String,Object> map) {
        map.put("hello", "Hi, boy!");
        Locale locale = LocaleContextHolder.getLocale();
        String country = messageSource.getMessage("country", null, locale);
        map.put("country", country);
        return "index";
    }

    @RequestMapping("/do")
    @ResponseBody
    public String doSth() {
        return userConfig.getWelcome();
    }

}
