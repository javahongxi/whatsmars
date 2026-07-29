package org.hongxi.whatsmars.nacos.naming;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Nacos Naming Service 配置类
 */
@Configuration(proxyBeanMethods = false)
public class NamingConfig {

    @Value("${nacos.server-addr:127.0.0.1:8848}")
    private String serverAddr;

    @Value("${nacos.username:}")
    private String username;

    @Value("${nacos.password:}")
    private String password;

    @Value("${nacos.namespace:}")
    private String namespace;

    @Bean(destroyMethod = "shutDown")
    public NamingService namingService() throws NacosException {
        Properties properties = new Properties();
        properties.setProperty("serverAddr", serverAddr);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        if (namespace != null && !namespace.isEmpty()) {
            properties.setProperty("namespace", namespace);
        }

        return NacosFactory.createNamingService(properties);
    }
}
