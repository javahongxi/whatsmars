package org.hongxi.whatsmars.reactor.core;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class ReactiveContextTest {

    @Test
    public void showcaseContext() {
        printCurrentContext("top")
                .subscriberContext(Context.of("top", "context"))
                .flatMap(__ -> printCurrentContext("middle"))
                .subscriberContext(Context.of("middle", "context"))
                .flatMap(__ -> printCurrentContext("bottom"))
                .subscriberContext(Context.of("bottom", "context"))
                .flatMap(__ -> printCurrentContext("initial"))
                .block();
    }

    void print(String id, Context context) {
        System.out.println(id + " {");
        System.out.print("  ");
        System.out.println(context);
        System.out.println("}");
        System.out.println();
    }

    Mono<Context> printCurrentContext(String id) {
        return Mono
                .subscriberContext()
                .doOnNext(context -> print(id, context));
    }
}
