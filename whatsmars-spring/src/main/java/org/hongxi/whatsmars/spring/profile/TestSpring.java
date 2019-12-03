package org.hongxi.whatsmars.spring.profile;

import org.hongxi.whatsmars.common.profile.ProfileUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSpring {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(PropertyConfiguration.class);
        context.scan("org.hongxi.whatsmars.spring.profile");
        context.getEnvironment().setActiveProfiles(ProfileUtils.getProfile());
        context.refresh();
    }
}
