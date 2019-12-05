package org.hongxi.whatsmars.spring.data;

import org.hongxi.whatsmars.common.profile.ProfileUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:spring-*.xml")
@Configuration
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(Application.class.getPackage().getName());
        context.getEnvironment().setActiveProfiles(ProfileUtils.getProfile());
        context.refresh();
    }
}
