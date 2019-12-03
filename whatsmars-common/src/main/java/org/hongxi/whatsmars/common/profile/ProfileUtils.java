package org.hongxi.whatsmars.common.profile;

public class ProfileUtils {

    private static final String PROFILE_ENV_NAME = "PROFILE";
    private static final String DEFAULT_PROFILE = "test";

    public static String getProfile() {
        String profile = System.getProperty(PROFILE_ENV_NAME);
        return profile == null || profile.trim().length() == 0 ? DEFAULT_PROFILE : profile;
    }
}
