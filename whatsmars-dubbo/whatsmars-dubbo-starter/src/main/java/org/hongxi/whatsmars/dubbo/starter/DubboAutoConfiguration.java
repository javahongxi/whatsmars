package org.hongxi.whatsmars.dubbo.starter;

import com.alibaba.dubbo.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration {

	@Autowired
	private DubboProperties dubboProperties;

	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = dubboProperties.getApplication();
		if (applicationConfig == null) {
			applicationConfig = new ApplicationConfig();
		}
		return applicationConfig;
	}

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = dubboProperties.getRegistry();
		if (registryConfig == null) {
			registryConfig = new RegistryConfig();
		}
		return registryConfig;
	}

	@Bean
	public ProtocolConfig protocolConfig() {
		ProtocolConfig protocolConfig = dubboProperties.getProtocol();
		if (protocolConfig == null) {
			protocolConfig = new ProtocolConfig();
		}
		return protocolConfig;
	}

	@Bean
	public MonitorConfig monitorConfig() {
		MonitorConfig monitorConfig = dubboProperties.getMonitor();
		if (monitorConfig == null) {
			monitorConfig = new MonitorConfig();
		}
		return monitorConfig;
	}

	@Bean
	public ProviderConfig providerConfig() {
		ProviderConfig providerConfig = dubboProperties.getProvider();
		if (providerConfig == null) {
			providerConfig = new ProviderConfig();
		}
		return providerConfig;
	}

	@Bean
	public ModuleConfig moduleConfig() {
		ModuleConfig moduleConfig = dubboProperties.getModule();
		if (moduleConfig == null) {
			moduleConfig = new ModuleConfig();
		}
		return moduleConfig;
	}

	@Bean
	public MethodConfig methodConfig() {
		MethodConfig methodConfig = dubboProperties.getMethod();
		if (methodConfig == null) {
			methodConfig = new MethodConfig();
		}
		return methodConfig;
	}

	@Bean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = dubboProperties.getConsumer();
		if (consumerConfig == null) {
			consumerConfig = new ConsumerConfig();
		}
		return consumerConfig;
	}

}
