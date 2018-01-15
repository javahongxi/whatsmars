package org.hongxi.whatsmars.dubbo.starter;

import com.alibaba.dubbo.config.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.dubbo")
public class DubboProperties {

    private String scan;

    private ApplicationConfig application;

    private RegistryConfig registry;

    private ProtocolConfig protocol;

    private MonitorConfig monitor;
    
    private ProviderConfig provider;
    
    private ModuleConfig module;
    
    private MethodConfig method;
    
    private ConsumerConfig consumer;

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
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

	public ModuleConfig getModule() {
		return module;
	}

	public void setModule(ModuleConfig module) {
		this.module = module;
	}

	public MethodConfig getMethod() {
		return method;
	}

	public void setMethod(MethodConfig method) {
		this.method = method;
	}

	public ConsumerConfig getConsumer() {
		return consumer;
	}

	public void setConsumer(ConsumerConfig consumer) {
		this.consumer = consumer;
	}
}
