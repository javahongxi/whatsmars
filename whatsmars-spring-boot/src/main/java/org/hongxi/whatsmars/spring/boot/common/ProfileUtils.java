package org.hongxi.whatsmars.spring.boot.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by shenhongxi on 2017/8/2.
 */
@Component
public class ProfileUtils implements InitializingBean {

    @Autowired
    private Environment env;

    private static final String PROFILE_PROD = "prod";
    private static final String PROFILE_TEST = "test";
    private static final String PROFILE_DEV = "dev";

    private static List<String> profiles = Collections.unmodifiableList(new ArrayList<String>());

    @Override
    public void afterPropertiesSet() throws Exception {
        profiles = Collections.unmodifiableList(Arrays.asList(env.getActiveProfiles()));
    }

    public static boolean isProd() {
        return profiles.contains(PROFILE_PROD);
    }

    public static boolean isTest() {
        return profiles.contains(PROFILE_TEST);
    }

    public static boolean isDev() {
        return profiles.contains(PROFILE_DEV);
    }

    public static List<String> getProfiles() {
        return profiles;
    }

}
