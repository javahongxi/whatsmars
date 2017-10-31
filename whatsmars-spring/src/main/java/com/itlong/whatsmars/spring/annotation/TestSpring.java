package com.itlong.whatsmars.spring.annotation;

import com.itlong.whatsmars.spring.model.Mars;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by javahongxi on 2017/10/31.
 */
public class TestSpring {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        Mars mars = ctx.getBean(Mars.class);
        System.out.println(mars.getCnName());
    }
}
