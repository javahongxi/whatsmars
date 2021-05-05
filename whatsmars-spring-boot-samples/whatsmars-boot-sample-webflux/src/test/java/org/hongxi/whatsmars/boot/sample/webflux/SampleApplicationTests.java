package org.hongxi.whatsmars.boot.sample.webflux;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hongxi.whatsmars.boot.sample.webflux.model.Order;
import org.junit.jupiter.api.Test;
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
class SampleApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Test
	public void shouldBeAbleToPostOrder() {
		Order order = new Order("123456");
		webTestClient
				.post()
				.uri("/order/create?userId=123")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(order))
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void shouldBeNotFound() {
		webTestClient
				.get()
				.uri("/order/list?userId=123")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	public void shouldBeAbleToFindOrder() {
		shouldBeAbleToPostOrder();
		webTestClient
				.get()
				.uri("/order/detail/123456?userId=123")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Order.class)
				.consumeWith(e -> MatcherAssert.assertThat(e.getResponseBody(), Matchers.hasProperty("id", Matchers.equalTo("123456"))));
	}

}