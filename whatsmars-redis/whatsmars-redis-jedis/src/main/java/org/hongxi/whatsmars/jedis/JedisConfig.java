package org.hongxi.whatsmars.jedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis 连接池配置
 */
@Configuration(proxyBeanMethods = false)
public class JedisConfig {

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool() {
        String host = System.getProperty("redis.host", "localhost");
        int port = Integer.parseInt(System.getProperty("redis.port", "6379"));
        String password = System.getProperty("redis.password", null);

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        if (password != null && !password.isEmpty()) {
            return new JedisPool(config, host, port, 3000, password);
        }
        return new JedisPool(config, host, port, 3000);
    }
}
