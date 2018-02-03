package org.hongxi.whatsmars.mq.rabbitmq.boot;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication
@RabbitListener(queues = "foo")
@EnableScheduling
public class SampleAmqpSimpleApplication {

	@Bean
	public Sender mySender() {
		return new Sender();
	}

	@Bean
	public Queue fooQueue() {
		return new Queue("foo");
	}

	@RabbitHandler
	public void process(@Payload String foo) {
		System.out.println(new Date() + ": " + foo);
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleAmqpSimpleApplication.class, args);
	}

}