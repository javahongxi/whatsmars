package org.hongxi.whatsmars.boot.sample.webflux;

import reactor.core.publisher.Hooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WebFlux 更多解决方案见 https://github.com/javahongxi/webflux-sample
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Hooks.onOperatorDebug();
		SpringApplication.run(DemoApplication.class, args);
	}
}