package org.hongxi.whatsmars.spring.profile;

import org.springframework.util.StringUtils;

public class ProfileUtils {

    private static final String PROFILE_ENV_NAME = "PROFILE";
    private static final String DEFAULT_PROFILE = "test";

    public static String getProfile() {
        String profile = System.getProperty(PROFILE_ENV_NAME);
        return StringUtils.isEmpty(profile) ? DEFAULT_PROFILE : profile;
    }
}
