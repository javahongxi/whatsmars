
package org.hongxi.whatsmars.dubbo.starter.context.event;

import com.alibaba.dubbo.common.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

import static org.hongxi.whatsmars.dubbo.starter.util.DubboUtils.*;
import static com.alibaba.dubbo.qos.server.DubboLogo.dubbo;

/**
 * Dubbo Welcome Logo {@link ApplicationListener}
 *
 */
@Order(LoggingApplicationListener.DEFAULT_ORDER + 1)
public class WelcomeLogoApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        /**
         * Gets Logger After LoggingSystem configuration ready
         * @see LoggingApplicationListener
         */
        final Logger logger = LoggerFactory.getLogger(getClass());

        String bannerText = buildBannerText();

        if (logger.isInfoEnabled()) {
            logger.info(bannerText);
        } else {
            System.out.print(bannerText);
        }

    }


    String buildBannerText() {

        StringBuilder bannerTextBuilder = new StringBuilder();

        bannerTextBuilder
                .append(LINE_SEPARATOR)
                .append(LINE_SEPARATOR)
                .append(dubbo)
                .append(" :: Dubbo Spring Boot (v").append(Version.getVersion(getClass(), "1.0.0")).append(") : ")
                .append(DUBBO_SPRING_BOOT_GITHUB_URL)
                .append(LINE_SEPARATOR)
                .append(" :: Dubbo (v").append(Version.getVersion()).append(") : ")
                .append(DUBBO_GITHUB_URL)
                .append(LINE_SEPARATOR)
                .append(" :: Google group : ")
                .append(DUBBO_GOOGLE_GROUP_URL)
                .append(LINE_SEPARATOR)
        ;

        return bannerTextBuilder.toString();

    }

}
