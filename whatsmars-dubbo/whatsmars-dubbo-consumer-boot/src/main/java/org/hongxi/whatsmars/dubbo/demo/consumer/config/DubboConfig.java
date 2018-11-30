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
     * @param otherZK
     * @return
     */
    @Bean("otherZK")
    public RegistryConfig registryConfig(@Value("${registry.zk.other}") String otherZK) {
        RegistryConfig registry = new RegistryConfig();
        registry.setId("otherZK");
        registry.setAddress(otherZK);
        return registry;
    }
}
