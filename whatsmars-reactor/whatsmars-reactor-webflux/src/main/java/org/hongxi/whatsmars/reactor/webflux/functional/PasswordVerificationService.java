package org.hongxi.whatsmars.reactor.webflux.functional;

import reactor.core.publisher.Mono;

public interface PasswordVerificationService {

    Mono<Void> check(String raw, String encoded);
}
