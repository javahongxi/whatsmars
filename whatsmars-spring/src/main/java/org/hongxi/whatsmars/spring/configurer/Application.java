package org.hongxi.whatsmars.spring.configurer;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;

/**
 * Spring Configuration entry point.
 *
 * <p>Demonstrates:</p>
 * <ul>
 *   <li>{@code @Configuration} + {@code @ComponentScan}</li>
 *   <li>{@code @Import} - composing multiple Java-based configs</li>
 *   <li>{@code @ImportResource} - importing XML-based bean definitions</li>
 *   <li>{@code Environment} property source setup for YAML configuration</li>
 * </ul>
 *
 * @see AppConfig
 * @see DataSourceProperties
 * @see ConditionalConfig
 */
@ComponentScan(basePackages = "org.hongxi.whatsmars.spring.configurer")
@Import({AppConfig.class, DataSourceProperties.class, ConditionalConfig.class})
@ImportResource("classpath:spring-context.xml")
@Configuration(proxyBeanMethods = false)
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // Load YAML properties into the Environment BEFORE refresh,
        // so that ConditionContext.getEnvironment().getProperty() can resolve them.
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        PropertiesPropertySource yamlPropertySource =
                new PropertiesPropertySource("yaml", Objects.requireNonNull(yaml.getObject()));
        ctx.getEnvironment().getPropertySources().addFirst(yamlPropertySource);

        ctx.register(Application.class);
        ctx.refresh();

        System.out.println("===== Configuration Properties =====");
        System.out.println("spring.application.name = "
                + ctx.getEnvironment().getProperty("spring.application.name"));

        System.out.println("\n===== @PropertySource + @Bean lifecycle =====");
        AppConfig.AppInfo appInfo = ctx.getBean(AppConfig.AppInfo.class);
        System.out.println(appInfo);

        System.out.println("\n===== @Lazy bean (created on first access) =====");
        String greeting = ctx.getBean("lazyGreeting", String.class);
        System.out.println(greeting);

        System.out.println("\n===== @Scope(\"prototype\") - each getBean returns a new instance =====");
        AppConfig.RequestTrace trace1 = ctx.getBean(AppConfig.RequestTrace.class);
        AppConfig.RequestTrace trace2 = ctx.getBean(AppConfig.RequestTrace.class);
        System.out.println("trace1=" + trace1 + ", trace2=" + trace2);

        System.out.println("\n===== @DependsOn + @Primary DataSource =====");
        DataSourceProperties.DataSourceConfig ds = ctx.getBean(DataSourceProperties.DataSourceConfig.class);
        System.out.println(ds);

        System.out.println("\n===== @Conditional bean (feature.cache.enabled) =====");
        if (ctx.containsBean("cacheManager")) {
            ConditionalConfig.CacheManager cacheManager = ctx.getBean(ConditionalConfig.CacheManager.class);
            cacheManager.put("demo", "hello");
        } else {
            System.out.println("cacheManager not registered (feature.cache.enabled != true)");
        }

        ctx.close();
    }
}
