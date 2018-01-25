
package org.hongxi.whatsmars.dubbo.actuate.autoconfigure;

import com.alibaba.dubbo.config.annotation.Service;
import org.hongxi.whatsmars.dubbo.actuate.endpoint.DubboEndpoint;
import org.hongxi.whatsmars.dubbo.actuate.endpoint.mvc.DubboMvcEndpoint;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * Dubbo {@link MvcEndpoint} {@link ManagementContextConfiguration}
 *
 * @see
 */
@ManagementContextConfiguration
@ConditionalOnClass(Service.class)
@ConditionalOnWebApplication
public class DubboMvcEndpointManagementContextConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DubboMvcEndpoint dubboMvcEndpoint(DubboEndpoint dubboEndpoint) {
        return new DubboMvcEndpoint(dubboEndpoint);
    }


}
