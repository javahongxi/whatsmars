package org.hongxi.whatsmars.reactor.webflux;

import org.hongxi.whatsmars.reactor.webflux.functional.DefaultPasswordVerificationService;
import org.hongxi.whatsmars.reactor.webflux.functional.StandaloneApplication;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

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