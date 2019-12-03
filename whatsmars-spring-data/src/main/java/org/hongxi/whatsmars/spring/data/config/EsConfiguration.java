package org.hongxi.whatsmars.spring.data.config;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;

import java.util.Properties;

@Configuration
public class EsConfiguration {

    @Value("${es.cluster.name}")
    private String clusterName;
    @Value("${es.cluster.nodes}")
    private String clusterNodes;

    @Bean
    public TransportClient transportClient() throws Exception {
        TransportClientFactoryBean factory = new TransportClientFactoryBean();
        factory.setClusterNodes(clusterNodes);
        factory.setProperties(createProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Properties createProperties() {
        Properties properties = new Properties();
        properties.put("cluster.name", clusterName);
        return properties;
    }
}
