package org.hongxi.whatsmars.boot.sample.thymeleaf.controller;

import org.hongxi.whatsmars.boot.sample.thymeleaf.common.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Controller
public class HomeController {

    @Autowired
    private LocaleService localeService;

    /**
     * 设置区域解析器 (default is AcceptHeaderLocaleResolver)
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("language");
        localeResolver.setCookieMaxAge(Integer.MAX_VALUE);
        localeResolver.setDefaultLocale(LocaleService.DEFAULT_LOCALE); // Locale.getDefault()
        return localeResolver;
    }

    @PostMapping(value = "/changeLang")
    @ResponseBody
    public String changeLang(HttpServletRequest request, HttpServletResponse response, @RequestParam String lang){
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if ("zh".equals(lang)) {
            localeResolver.setLocale(request, response, Locale.SIMPLIFIED_CHINESE);
        } else if("en".equals(lang)){
            localeResolver.setLocale(request, response, Locale.US);
        }
        return "lang:" + LocaleContextHolder.getLocale().getLanguage();
    }

    @GetMapping("/")
    public String home(Map<String,Object> map) {
        System.out.println(LocaleService.isUSLocale());
        map.put("hello", "Hi, boy!");
        map.put("country", localeService.getMessage("country"));
        return "index";
    }
}
