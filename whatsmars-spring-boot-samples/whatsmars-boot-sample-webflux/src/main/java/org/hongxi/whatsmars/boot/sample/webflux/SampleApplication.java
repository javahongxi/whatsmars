package org.hongxi.whatsmars.boot.sample.webflux;

import reactor.core.publisher.Hooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		Hooks.onOperatorDebug();
		SpringApplication.run(SampleApplication.class, args);
	}
}