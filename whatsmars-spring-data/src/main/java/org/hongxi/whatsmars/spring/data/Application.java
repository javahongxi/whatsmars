package org.hongxi.whatsmars.spring.data;

import org.hongxi.whatsmars.common.profile.ProfileUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("org.hongxi.whatsmars.spring.data");
        context.getEnvironment().setActiveProfiles(ProfileUtils.getProfile());
        context.refresh();
    }
}
