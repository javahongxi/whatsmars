package org.hongxi.whatsmars.dubbo.demo.consumer.config;

import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenhongxi on 2018/11/30.
 */
@Configuration
public class DubboConfig {

    /**
     * 配置文件里配置默认的，这里配置其他需要的
     */
    @Bean("otherRegistry")
    public RegistryConfig otherRegistry(@Value("${registry.other.protocol}") String protocol,
                                        @Value("${registry.other.address}") String address,
                                        @Value("${registry.other.id}") String id) {
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(protocol);
        registry.setAddress(address);
        registry.setId(id);
        return registry;
    }
}
