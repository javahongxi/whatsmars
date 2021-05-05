package org.hongxi.whatsmars.boot.sample.web.support;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class WebUtils {

    public static final String[] EXCLUDE_RESOURCE_PATHS = new String[]{
            "/error",
            "/error/**",
            "/actuator/**"
    };

    public static final String PATH_PATTERN_ATTR = qualify("pathPattern");
    public static final String SHOULD_NOT_FILTER_ATTR = qualify("shouldNotFilter");

    private static String qualify(String attr) {
        return WebUtils.class.getName() + "." + attr;
    }
}
