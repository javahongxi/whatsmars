package org.hongxi.whatsmars.spring.context;

import org.hongxi.whatsmars.spring.model.Mars;
import org.hongxi.whatsmars.spring.task.DemoTask;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by shenhongxi on 2016/4/7.
 */
public class TestSpring {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        Mars mars = (Mars) context.getBean("mars");
        System.out.println(mars.getAge());
        System.out.println(mars.getCnName());

        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        taskExecutor.execute(new DemoTask());
    }
}
