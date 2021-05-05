package org.hongxi.whatsmars.boot.sample.webflux.router;

import org.hongxi.whatsmars.boot.sample.webflux.router.handler.OrderHandler;
import org.hongxi.whatsmars.boot.sample.webflux.router.handler.ServerRedirectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by shenhongxi on 2021/4/22.
 */
@Configuration
public class RouterConfiguration {

    final ServerRedirectHandler serverRedirectHandler = new ServerRedirectHandler();

    @Bean
    public RouterFunction<ServerResponse> routes(OrderHandler orderHandler) {
        return
                nest(path("/func/order"),
                        nest(accept(APPLICATION_JSON),
                                route(GET("/{id}"), orderHandler::get)
                                        .andRoute(method(HttpMethod.GET), orderHandler::list)
                        )
                                .andNest(contentType(APPLICATION_JSON),
                                        route(POST("/create"), orderHandler::create)
                                )
                                .andNest((serverRequest) -> serverRequest.cookies()
                                                .containsKey("Redirect-Traffic"),
                                        route(all(), serverRedirectHandler)
                                )
                );
    }
}
