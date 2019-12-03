package org.hongxi.whatsmars.spring.data;

import org.hongxi.whatsmars.common.profile.ProfileUtils;
import org.hongxi.whatsmars.spring.data.config.PropertyConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(PropertyConfiguration.class);
        context.scan("org.hongxi.whatsmars.spring.data");
        context.getEnvironment().setActiveProfiles(ProfileUtils.getProfile());
        context.refresh();
    }
}
