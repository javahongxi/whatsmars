package org.hongxi.whatsmars.spring.aspect.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Demonstrates Spring AOP with AspectJ:
 * <ul>
 *   <li>{@code @EnableAspectJAutoProxy} - enables AspectJ auto-proxy support</li>
 *   <li>Normal return: all five advice types fire ({@code @Before}, {@code @Around}, {@code @After}, {@code @AfterReturning})</li>
 *   <li>Exception: {@code @AfterThrowing} fires instead of {@code @AfterReturning}</li>
 * </ul>
 */
@ComponentScan(basePackages = "org.hongxi.whatsmars.spring.aspect")
@EnableAspectJAutoProxy
@Configuration(proxyBeanMethods = false)
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Application.class);
        ctx.refresh();

        DemoService demoService = ctx.getBean(DemoService.class);

        System.out.println("===== Scenario 1: Normal return (greet) =====");
        String greeting = demoService.greet("Spring");
        System.out.println("  -> " + greeting);

        System.out.println("\n===== Scenario 2: Normal return with args (compute) =====");
        int result = demoService.compute(17, 25);
        System.out.println("  -> " + result);

        System.out.println("\n===== Scenario 3: Exception (@AfterThrowing) =====");
        try {
            demoService.fail();
        } catch (RuntimeException e) {
            System.out.println("  -> Caught: " + e.getMessage());
        }

        ctx.close();
    }
}
