
package org.hongxi.whatsmars.dubbo.starter.context.event;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.SortedMap;

import static org.hongxi.whatsmars.dubbo.starter.util.DubboUtils.*;

/**
 * {@link ApplicationListener} to override the dubbo properties from {@link Environment}into
 * {@link ConfigUtils#getProperties() Dubbo Config}.
 * {@ConfigUtils Dubbo Config} on {@link ApplicationEnvironmentPreparedEvent}.
 * <p>
 *
 * @see ConfigUtils
 */
@Order // LOWEST_PRECEDENCE Make sure last execution
public class OverrideDubboConfigApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        /**
         * Gets Logger After LoggingSystem configuration ready
         * @see LoggingApplicationListener
         */
        final Logger logger = LoggerFactory.getLogger(getClass());

        ConfigurableEnvironment environment = event.getEnvironment();

        boolean override = environment.getProperty(OVERRIDE_CONFIG_PROPERTY_NAME, boolean.class,
                DEFAULT_OVERRIDE_CONFIG_PROPERTY_VALUE);

        if (override) {

            SortedMap<String, Object> dubboProperties = filterDubboProperties(environment);

            ConfigUtils.getProperties().putAll(dubboProperties);

            if (logger.isInfoEnabled()) {

                logger.info("Dubbo Config was overridden by externalized configuration {}", dubboProperties);

            }

        } else {

            if (logger.isInfoEnabled()) {

                logger.info("Disable override Dubbo Config caused by property {} = {}", OVERRIDE_CONFIG_PROPERTY_NAME, override);

            }

        }

    }

}
