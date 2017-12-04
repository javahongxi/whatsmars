package com.whatsmars.spring.boot.common;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Created by shenhongxi on 2017/7/31.
 */
public class LocaleUtils {

    public static boolean isEnLocale() {
        return LocaleContextHolder.getLocale().getLanguage().equals("en");
    }
}
