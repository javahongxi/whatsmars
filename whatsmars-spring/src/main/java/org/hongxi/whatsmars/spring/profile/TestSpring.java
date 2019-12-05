package org.hongxi.whatsmars.spring.profile;

import org.hongxi.whatsmars.common.profile.ProfileUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestSpring {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(TestSpring.class.getPackage().getName());
        context.getEnvironment().setActiveProfiles(ProfileUtils.getProfile());
        context.refresh();
    }
}
