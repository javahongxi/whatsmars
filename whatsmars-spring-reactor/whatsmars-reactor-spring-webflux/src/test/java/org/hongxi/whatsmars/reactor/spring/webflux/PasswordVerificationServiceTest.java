package org.hongxi.whatsmars.reactor.spring.webflux;

import java.time.Duration;

import org.hongxi.whatsmars.reactor.spring.webflux.functional.DefaultPasswordVerificationService;
import org.hongxi.whatsmars.reactor.spring.webflux.functional.StandaloneApplication;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

public class PasswordVerificationServiceTest {

    @Before
    public void setUp() throws InterruptedException {
        new Thread(StandaloneApplication::main).start();
        Thread.sleep(1000);
    }

    @Test
    public void checkApplicationRunning() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(18);
        DefaultPasswordVerificationService service =
                new DefaultPasswordVerificationService(WebClient.builder());

        StepVerifier.create(service.check("test", encoder.encode("test")))
                    .expectSubscription()
                    .expectComplete()
                    .verify(Duration.ofSeconds(30));

    }
}