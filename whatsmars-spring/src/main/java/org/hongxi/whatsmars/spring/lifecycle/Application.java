package org.hongxi.whatsmars.spring.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Demonstrates the complete Spring bean lifecycle.
 *
 * <p><b>Bean initialization order (per bean):</b>
 * <ol>
 *   <li>Constructor — bean instantiation</li>
 *   <li>Property injection — setter / field injection</li>
 *   <li>{@code BeanNameAware} / {@code BeanFactoryAware} / {@code ApplicationContextAware}</li>
 *   <li>{@code BeanPostProcessor.postProcessBeforeInitialization()}</li>
 *   <li>{@code @PostConstruct}</li>
 *   <li>{@code InitializingBean.afterPropertiesSet()}</li>
 *   <li>{@code @Bean(initMethod = "...")}</li>
 *   <li>{@code BeanPostProcessor.postProcessAfterInitialization()}</li>
 * </ol>
 *
 * <p><b>After all beans initialized:</b>
 * <ol start="9">
 *   <li>{@code SmartLifecycle.start()} — auto-start components</li>
 * </ol>
 *
 * <p><b>Context shutdown order:</b>
 * <ol>
 *   <li>{@code SmartLifecycle.stop()}</li>
 *   <li>{@code @PreDestroy}</li>
 *   <li>{@code DisposableBean.destroy()}</li>
 *   <li>{@code @Bean(destroyMethod = "...")}</li>
 * </ol>
 */
@ComponentScan(basePackages = "org.hongxi.whatsmars.spring.lifecycle")
@Configuration(proxyBeanMethods = false)
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Application.class);
        ctx.refresh();

        ctx.close();
    }

    /**
     * Registers a third-party bean with custom init/destroy methods.
     * This is the recommended approach for beans you cannot modify.
     */
    @Bean(initMethod = "connect", destroyMethod = "disconnect")
    public ExternalService externalService() {
        ExternalService service = new ExternalService();
        service.setName("payment-gateway");
        return service;
    }
}
