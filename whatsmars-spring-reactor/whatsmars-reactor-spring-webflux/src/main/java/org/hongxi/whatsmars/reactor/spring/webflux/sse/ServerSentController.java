package org.hongxi.whatsmars.reactor.spring.webflux.sse;

import java.util.Map;

import reactor.core.publisher.Flux;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerSentController {

    private Map<String, StocksService> stringStocksServiceMap;

    @GetMapping("/sse/stocks")
    public Flux<ServerSentEvent<?>> streamStocks() {
        return Flux
            .fromIterable(stringStocksServiceMap.values())
            .flatMap(StocksService::stream)
            .<ServerSentEvent<?>>map(item ->
                ServerSentEvent
                    .builder(item)
                    .event("StockItem")
                    .id(item.getId())
                    .build()
            )
            .startWith(
                ServerSentEvent
                    .builder()
                    .event("Stocks")
                    .data(stringStocksServiceMap.keySet())
                    .build()
            );
    }
}
