package org.hongxi.whatsmars.boot.sample.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by shenhongxi on 2020/7/17.
 */
public class ActuatorEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        resolveManagement(environment);
        resolveSecurity(environment);
    }

    private void resolveSecurity(final ConfigurableEnvironment environment) {
        if (!environment.containsProperty("spring.security.user.name")) {
            System.setProperty("spring.security.user.name", "application");
        }
        if (!environment.containsProperty("spring.security.user.password")) {
            System.setProperty("spring.security.user.password", "whatsmars-spring-boot");
        }
        if (!environment.containsProperty("spring.security.user.roles")) {
            System.setProperty("spring.security.user.roles", "application");
        }
    }

    private void resolveManagement(final ConfigurableEnvironment environment) {
        // 有关Endpoints安全问题
        String excludes = environment.getProperty("spring.autoconfigure.exclude");
        StringBuilder sb = new StringBuilder();
        if (excludes != null) {
            sb.append(",");
        }
        // 关闭spring-boot默认的security配置
        sb.append(ManagementWebSecurityAutoConfiguration.class.getName());
        System.setProperty("spring.autoconfigure.exclude", sb.toString());

        // 默认全部关闭
        System.setProperty("management.endpoints.enabled-by-default", FALSE);

        System.setProperty("management.endpoints.web.exposure.include", "*");
        System.setProperty("management.endpoints.web.exposure.exclude", "shutdown,threaddump,heapdump");

        System.setProperty("management.endpoint.health.enabled", TRUE);
        System.setProperty("management.endpoint.health.show-details", "never");
        System.setProperty("management.endpoint.info.enabled", TRUE);

        System.setProperty("management.endpoint.mappings.enabled", TRUE);
        System.setProperty("management.endpoint.env.enabled", TRUE);
        System.setProperty("management.endpoint.conditions.enabled", TRUE);
        System.setProperty("management.endpoint.configprops.enabled", TRUE);
        System.setProperty("management.endpoint.beans.enabled", TRUE);
        System.setProperty("management.endpoint.loggers.enabled", TRUE);
        System.setProperty("management.endpoint.metrics.enabled", TRUE);

        System.setProperty("management.metrics.enable.http", FALSE);
        System.setProperty("management.metrics.enable.log4j2", FALSE);

        System.setProperty("server.tomcat.mbeanregistry.enabled", TRUE);
    }
}
