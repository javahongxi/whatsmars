package org.hongxi.whatsmars.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by shenhongxi on 2020/7/17.
 */
public class SystemUtils {

    public static String getProperty(String key) {
        return System.getProperty(key, System.getenv(key));
    }

    public static boolean contains(String key) {
        return StringUtils.isNotBlank(getProperty(key));
    }

    public static String getProperty(String key, String def) {
        String val = getProperty(key);
        return StringUtils.isNotBlank(val) ? val : def;
    }
}
