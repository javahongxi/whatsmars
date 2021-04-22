package org.hongxi.whatsmars.boot.sample.webflux;

import org.hamcrest.Matchers;
import org.hongxi.whatsmars.boot.sample.webflux.model.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class DemoApplicationTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void shouldBeAbleToPostOrder() {
        Order order = new Order("1");
        webTestClient
                .post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(order))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("location", "/orders/1");
    }

    @Test
    public void shouldNotBeNotFoundInCaseOfWrongContentType() {
        webTestClient
                .post()
                .uri("/orders")
                .contentType(MediaType.TEXT_HTML)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    public void shouldBeAbleToFindOrder() {
        shouldBeAbleToPostOrder();
        webTestClient
                .get()
                .uri("/orders/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Order.class)
                .consumeWith(e -> Assert.assertThat(e.getResponseBody(), Matchers.hasProperty("id", Matchers.equalTo("1"))));
    }
}
