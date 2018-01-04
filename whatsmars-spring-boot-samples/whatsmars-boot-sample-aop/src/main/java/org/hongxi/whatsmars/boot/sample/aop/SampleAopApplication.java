package org.hongxi.whatsmars.boot.sample.aop;

import org.hongxi.whatsmars.boot.sample.aop.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleAopApplication implements CommandLineRunner {

	// Simple example shows how an application can spy on itself with AOP

	@Autowired
    private HelloWorldService helloWorldService;

	@Override
	public void run(String... args) {
		System.out.println(this.helloWorldService.getHelloMessage());
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleAopApplication.class, args);
	}

}
