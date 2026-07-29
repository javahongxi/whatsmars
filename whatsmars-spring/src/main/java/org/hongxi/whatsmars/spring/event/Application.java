package org.hongxi.whatsmars.spring.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Demonstrates Spring's event mechanism:
 * <ul>
 *   <li>Publishing events via {@link org.springframework.context.ApplicationEventPublisher}</li>
 *   <li>Listening with {@code @EventListener} (Spring 4.2+, replaces {@code ApplicationListener})</li>
 *   <li>Ordering listeners with {@code @Order}</li>
 *   <li>Conditional listening with SpEL: {@code @EventListener(condition = "...")}</li>
 * </ul>
 */
@ComponentScan(basePackages = "org.hongxi.whatsmars.spring.event")
@Configuration(proxyBeanMethods = false)
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Application.class);
        ctx.refresh();

        OrderService orderService = ctx.getBean(OrderService.class);

        // Normal order — triggers Inventory + Notification listeners
        orderService.createOrder("Mechanical Keyboard", 2);

        // Large order — additionally triggers the Analytics listener (quantity >= 10)
        orderService.createOrder("USB-C Cable", 50);

        ctx.close();
    }
}
