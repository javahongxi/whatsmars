package org.hongxi.whatsmars.spring.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

/**
 * Demonstrates:
 * - {@code @PropertySource} for loading .properties files
 * - {@code @Bean} with initMethod / destroyMethod
 * - {@code @Lazy} for deferred bean initialization
 * - {@code @Scope} for prototype-scoped beans
 */
@Configuration(proxyBeanMethods = false)
@PropertySource(value = "classpath:config.properties")
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.description}")
    private String appDescription;

    @Value("${app.admin-email:admin@default.com}")
    private String adminEmail;

    /**
     * Bean with init and destroy lifecycle callbacks.
     */
    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public AppInfo appInfo() {
        AppInfo info = new AppInfo();
        info.setName(appName);
        info.setVersion(appVersion);
        info.setDescription(appDescription);
        info.setAdminEmail(adminEmail);
        return info;
    }

    /**
     * Lazy-initialized bean: only created when first requested.
     */
    @Lazy
    @Bean
    public String lazyGreeting() {
        System.out.println("[Lazy] lazyGreeting bean is being created now");
        return "Hello from " + appName + " v" + appVersion;
    }

    /**
     * Prototype-scoped bean: a new instance is created each time it is requested.
     */
    @Scope("prototype")
    @Bean
    public RequestTrace requestTrace() {
        return new RequestTrace();
    }

    // ---- Inner bean classes ----

    public static class AppInfo {
        private String name;
        private String version;
        private String description;
        private String adminEmail;

        public void init() {
            System.out.println("[AppInfo] initialized: " + name + " v" + version);
        }

        public void cleanup() {
            System.out.println("[AppInfo] cleanup: " + name);
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getAdminEmail() { return adminEmail; }
        public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

        @Override
        public String toString() {
            return String.format("AppInfo{name='%s', version='%s', description='%s', adminEmail='%s'}",
                    name, version, description, adminEmail);
        }
    }

    public static class RequestTrace {
        private static long counter = 0;
        private final long id;

        public RequestTrace() {
            this.id = ++counter;
            System.out.println("[RequestTrace] created instance #" + id);
        }

        public long getId() { return id; }

        @Override
        public String toString() {
            return "RequestTrace#" + id;
        }
    }
}
