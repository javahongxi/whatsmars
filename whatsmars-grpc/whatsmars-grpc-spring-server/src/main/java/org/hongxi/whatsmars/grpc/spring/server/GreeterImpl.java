package org.hongxi.whatsmars.grpc.spring.server;

import io.grpc.stub.StreamObserver;
import org.hongxi.whatsmars.grpc.api.helloworld.GreeterGrpc;
import org.hongxi.whatsmars.grpc.api.helloworld.HelloReply;
import org.hongxi.whatsmars.grpc.api.helloworld.HelloRequest;
import org.springframework.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

@GrpcService
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    private static final Logger log = LoggerFactory.getLogger(GreeterImpl.class);

    // Unary
    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        log.info("[Unary] Received request: {}", req.getName());
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    // Server streaming: one request -> multiple responses
    @Override
    public void sayHelloServerStream(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        log.info("[ServerStream] Received request: {}", req.getName());
        IntStream.rangeClosed(1, 5).forEach(i -> {
            HelloReply reply = HelloReply.newBuilder()
                    .setMessage("Hello " + req.getName() + " (" + i + "/5)")
                    .build();
            responseObserver.onNext(reply);
        });
        responseObserver.onCompleted();
    }

    // Client streaming: multiple requests -> one response
    @Override
    public StreamObserver<HelloRequest> sayHelloClientStream(StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<>() {
            private final StringBuilder names = new StringBuilder();

            @Override
            public void onNext(HelloRequest req) {
                log.info("[ClientStream] Received: {}", req.getName());
                if (!names.isEmpty()) {
                    names.append(", ");
                }
                names.append(req.getName());
            }

            @Override
            public void onError(Throwable t) {
                log.error("[ClientStream] Error", t);
            }

            @Override
            public void onCompleted() {
                HelloReply reply = HelloReply.newBuilder()
                        .setMessage("Hello " + names)
                        .build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            }
        };
    }

    // Bidirectional streaming: both sides stream
    @Override
    public StreamObserver<HelloRequest> sayHelloBidiStream(StreamObserver<HelloReply> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(HelloRequest req) {
                log.info("[BidiStream] Received: {}", req.getName());
                HelloReply reply = HelloReply.newBuilder()
                        .setMessage("Hello " + req.getName())
                        .build();
                responseObserver.onNext(reply);
            }

            @Override
            public void onError(Throwable t) {
                log.error("[BidiStream] Error", t);
            }

            @Override
            public void onCompleted() {
                log.info("[BidiStream] Client completed");
                responseObserver.onCompleted();
            }
        };
    }
}