package org.hongxi.whatsmars.boot.sample.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(redisNamespace = "sample")
@SpringBootApplication
public class SampleSessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleSessionApplication.class, args);
	}

}