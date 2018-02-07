package org.hongxi.whatsmars.spring.context.annotation;

import org.hongxi.whatsmars.spring.model.Earth;
import org.hongxi.whatsmars.spring.model.Mars;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;

/**
 * Created by javahongxi on 2017/10/31.
 * @Configuration is just a @Component
 * @see ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
 * @see AutowiredAnnotationBeanPostProcessor
 * @see CommonAnnotationBeanPostProcessor
 * @see RequiredAnnotationBeanPostProcessor
 * @see AnnotationConfigApplicationContext#scan(String...)
 */
public class TestSpring {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class, JustConfig.class);
        ctx.scan("org.hongxi.whatsmars.spring.context.annotation.repository");
        ctx.refresh();
        Mars mars = ctx.getBean(Mars.class);
        System.out.println(mars.getCnName());
        Earth earth = ctx.getBean(Earth.class);
        System.out.println(earth.getCnName());
    }
}
