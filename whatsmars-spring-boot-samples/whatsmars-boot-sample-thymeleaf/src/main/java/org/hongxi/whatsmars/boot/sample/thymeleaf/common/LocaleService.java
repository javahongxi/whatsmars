package org.hongxi.whatsmars.boot.sample.thymeleaf.common;

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

    public static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    public static boolean isUSLocale() {
        return LocaleContextHolder.getLocale().equals(Locale.US);
    }

    public static Locale resetLocale(Locale locale) {
        Locale origin = LocaleContextHolder.getLocale();
        LocaleContextHolder.setLocale(locale);
        return origin;
    }
}