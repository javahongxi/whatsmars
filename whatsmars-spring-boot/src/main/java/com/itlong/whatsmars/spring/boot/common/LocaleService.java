package com.itlong.whatsmars.spring.boot.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by shenhongxi on 2017/6/28.
 */
@Component
public class LocaleService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    public boolean isEnLocale() {
        return LocaleContextHolder.getLocale().getLanguage().equals("en");
    }
}
