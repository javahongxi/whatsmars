package org.hongxi.whatsmars.spring.aspect.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by shenhongxi on 2018/12/16.
 */
@ComponentScan(basePackages = "org.hongxi.whatsmars.spring.aspect")
@EnableAspectJAutoProxy
@Configuration
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Application.class);
        ctx.refresh();
        DemoService demoService = ctx.getBean(DemoService.class);
        demoService.t();
    }
}
