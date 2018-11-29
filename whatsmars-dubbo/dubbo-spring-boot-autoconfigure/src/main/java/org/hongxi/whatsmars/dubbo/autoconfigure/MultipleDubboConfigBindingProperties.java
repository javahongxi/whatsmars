
package org.hongxi.whatsmars.dubbo.autoconfigure;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfigBinding;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hongxi.whatsmars.dubbo.autoconfigure.util.DubboUtils.DUBBO_PREFIX;


/**
 * Multiple Dubbo Config Binding{@link ConfigurationProperties Properties} with prefix "dubbo."
 *
 *     registries:
 *       - address: zookeeper://127.0.0.1:2181
 *         id: r1
 *       - address: zookeeper://127.0.0.1:2182
 *         id: r2
 *
 * @see ConfigurationProperties
 * @see EnableDubboConfigBinding
 * @see EnableDubboConfig#multiple()
 */
@ConfigurationProperties(prefix = DUBBO_PREFIX)
public class MultipleDubboConfigBindingProperties {

    /**
     * Multiple {@link ApplicationConfig} property
     */
    
    private Map<String, ApplicationConfig> applications = new LinkedHashMap<>();

    /**
     * Multiple {@link ModuleConfig} property
     */
    
    private Map<String, ModuleConfig> modules = new LinkedHashMap<>();

    /**
     * Multiple {@link RegistryConfig} property
     */
    
    private Map<String, RegistryConfig> registries = new LinkedHashMap<>();

    /**
     * Multiple {@link ProtocolConfig} property
     */
    
    private Map<String, ProtocolConfig> protocols = new LinkedHashMap<>();

    /**
     * Multiple {@link MonitorConfig} property
     */
    
    private Map<String, MonitorConfig> monitors = new LinkedHashMap<>();

    /**
     * Multiple {@link ProviderConfig} property
     */
    
    private Map<String, ProviderConfig> providers = new LinkedHashMap<>();

    /**
     * Multiple {@link ConsumerConfig} property
     */
    
    private Map<String, ConsumerConfig> consumers = new LinkedHashMap<>();


    public Map<String, ApplicationConfig> getApplications() {
        return applications;
    }

    public void setApplications(Map<String, ApplicationConfig> applications) {
        this.applications = applications;
    }

    public Map<String, ModuleConfig> getModules() {
        return modules;
    }

    public void setModules(Map<String, ModuleConfig> modules) {
        this.modules = modules;
    }

    public Map<String, RegistryConfig> getRegistries() {
        return registries;
    }

    public void setRegistries(Map<String, RegistryConfig> registries) {
        this.registries = registries;
    }

    public Map<String, ProtocolConfig> getProtocols() {
        return protocols;
    }

    public void setProtocols(Map<String, ProtocolConfig> protocols) {
        this.protocols = protocols;
    }

    public Map<String, MonitorConfig> getMonitors() {
        return monitors;
    }

    public void setMonitors(Map<String, MonitorConfig> monitors) {
        this.monitors = monitors;
    }

    public Map<String, ProviderConfig> getProviders() {
        return providers;
    }

    public void setProviders(Map<String, ProviderConfig> providers) {
        this.providers = providers;
    }

    public Map<String, ConsumerConfig> getConsumers() {
        return consumers;
    }

    public void setConsumers(Map<String, ConsumerConfig> consumers) {
        this.consumers = consumers;
    }
}
