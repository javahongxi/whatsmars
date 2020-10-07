package org.hongxi.whatsmars.reactor.webflux.json;

import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;

import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;

public class ReactiveJsonParser {

    public static void main(String[] args) {
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder();

        decoder
            .decode(DataBufferUtils.readAsynchronousFileChannel(
                () -> AsynchronousFileChannel.open(Paths.get(ClassLoader.getSystemResource("LargeJsonFile.json").getPath()), StandardOpenOption.READ),
                new DefaultDataBufferFactory(),
                10
            ).delayElements(Duration.ofMillis(100)),
                    ResolvableType.forClass(Message.class),
                    null, null
            )
            .blockLast();
    }
}