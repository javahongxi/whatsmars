
package org.hongxi.whatsmars.dubbo.starter.autoconfigure;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfigBinding;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static org.hongxi.whatsmars.dubbo.starter.util.DubboUtils.DUBBO_PREFIX;


/**
 * Single Dubbo Config Binding{@link ConfigurationProperties Properties} with prefix "dubbo."
 *
 * @see ConfigurationProperties
 * @see EnableDubboConfigBinding
 */
@ConfigurationProperties(prefix = DUBBO_PREFIX)
public class SingleDubboConfigBindingProperties {

    /**
     * {@link ApplicationConfig} property
     */
    @NestedConfigurationProperty
    private ApplicationConfig application;

    /**
     * {@link ModuleConfig} property
     */
    @NestedConfigurationProperty
    private ModuleConfig module;

    /**
     * {@link RegistryConfig} property
     */
    @NestedConfigurationProperty
    private RegistryConfig registry;

    /**
     * {@link ProtocolConfig} property
     */
    @NestedConfigurationProperty
    private ProtocolConfig protocol;

    /**
     * {@link MonitorConfig} property
     */
    @NestedConfigurationProperty
    private MonitorConfig monitor;

    /**
     * {@link ProviderConfig} property
     */
    @NestedConfigurationProperty
    private ProviderConfig provider;

    /**
     * {@link ConsumerConfig} property
     */
    @NestedConfigurationProperty
    private ConsumerConfig consumer;

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public ModuleConfig getModule() {
        return module;
    }

    public void setModule(ModuleConfig module) {
        this.module = module;
    }

    public RegistryConfig getRegistry() {
        return registry;
    }

    public void setRegistry(RegistryConfig registry) {
        this.registry = registry;
    }

    public ProtocolConfig getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolConfig protocol) {
        this.protocol = protocol;
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }

    public ProviderConfig getProvider() {
        return provider;
    }

    public void setProvider(ProviderConfig provider) {
        this.provider = provider;
    }

    public ConsumerConfig getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfig consumer) {
        this.consumer = consumer;
    }

}
