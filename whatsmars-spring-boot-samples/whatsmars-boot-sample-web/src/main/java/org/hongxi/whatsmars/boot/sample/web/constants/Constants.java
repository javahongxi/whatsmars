package org.hongxi.whatsmars.boot.sample.web.constants;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class Constants {

    public static final int WEB_FIREWALL_FILTER_ORDER = -100;
    public static final int WEB_SESSION_FILTER_ORDER = -90;
    public static final int WEB_CRYPTO_FILTER_ORDER = -80;

    public static final String[] EXCLUDE_PATHS = new String[]{
            "/error",
            "/error/**",
            "/actuator/**",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/**/*.png",
            "/**/*.jpg",
            "/**/*.jpeg",
            "/**/*.gif",
            "/**/fonts/*",
            "/**/*.svg"
    };
}
