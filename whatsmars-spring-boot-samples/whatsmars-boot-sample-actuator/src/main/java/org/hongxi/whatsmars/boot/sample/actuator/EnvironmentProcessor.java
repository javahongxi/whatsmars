package org.hongxi.whatsmars.boot.sample.actuator;

import org.hongxi.whatsmars.common.util.SystemUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by shenhongxi on 2020/7/17.
 */
public class EnvironmentProcessor implements EnvironmentPostProcessor {
    public static final String PROP_METRICS_INCLUDE = "management.endpoints.web.exposure.include";
    public static final String PROP_METRICS_TAGS = "management.metrics.tags.application";
    public static final String PROP_TOMCAT_MBEAN_ENABLED = "server.tomcat.mbeanregistry.enabled";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!SystemUtils.contains(PROP_METRICS_INCLUDE)) {
            System.setProperty(PROP_METRICS_INCLUDE, "prometheus");
        }
        if (!SystemUtils.contains(PROP_METRICS_TAGS)) {
            System.setProperty(PROP_METRICS_TAGS, environment.getProperty("spring.application.name", "actuator-sample"));
        }
        if (!SystemUtils.contains(PROP_TOMCAT_MBEAN_ENABLED)) {
            System.setProperty(PROP_TOMCAT_MBEAN_ENABLED, "true");
        }
    }
}
