package org.hongxi.whatsmars.reactor.spring.webflux.webclient;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.reactive.function.client.WebClient;

public class TestWebClient {

    public static void main(String[] args) throws InterruptedException {
        WebClient.create("http://localhost:8080/api")                           // (1)
                 .get()                                                    // (2)
                 .uri("/users/{id}", 10)
                 .retrieve()
                 .bodyToMono(User.class)
                 .map(User::getUsername);

        Thread.sleep(1000);
    }
}
