package org.hongxi.whatsmars.spring.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

/**
 * Demonstrates type-safe configuration property binding
 * and bean dependency management with {@code @DependsOn} / {@code @Primary}.
 */
@Configuration(proxyBeanMethods = false)
public class DataSourceProperties {

    @Value("${datasource.url:jdbc:h2:mem:test}")
    private String url;

    @Value("${datasource.username:sa}")
    private String username;

    @Value("${datasource.password:}")
    private String password;

    @Value("${datasource.max-pool-size:10}")
    private int maxPoolSize;

    /**
     * Primary DataSource bean, depends on appInfo being initialized first.
     */
    @Primary
    @DependsOn("appInfo")
    @Bean
    public DataSourceConfig dataSourceConfig() {
        DataSourceConfig config = new DataSourceConfig();
        config.setUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaxPoolSize(maxPoolSize);
        System.out.println("[DataSourceConfig] created: " + config);
        return config;
    }

    /**
     * A secondary (read-only) DataSource for demonstration.
     */
    @Bean
    public DataSourceConfig readOnlyDataSourceConfig() {
        DataSourceConfig config = new DataSourceConfig();
        config.setUrl(url + "?readOnly=true");
        config.setUsername(username);
        config.setPassword(password);
        config.setMaxPoolSize(maxPoolSize / 2);
        System.out.println("[DataSourceConfig] readOnly created: " + config);
        return config;
    }

    // ---- Configuration holder class ----

    public static class DataSourceConfig {
        private String url;
        private String username;
        private String password;
        private int maxPoolSize;

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public int getMaxPoolSize() { return maxPoolSize; }
        public void setMaxPoolSize(int maxPoolSize) { this.maxPoolSize = maxPoolSize; }

        @Override
        public String toString() {
            return String.format("DataSourceConfig{url='%s', username='%s', maxPoolSize=%d}",
                    url, username, maxPoolSize);
        }
    }
}
