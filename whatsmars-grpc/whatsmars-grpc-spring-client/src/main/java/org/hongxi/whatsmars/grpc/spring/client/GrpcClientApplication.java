package org.hongxi.whatsmars.grpc.spring.client;

import io.grpc.stub.StreamObserver;
import org.hongxi.whatsmars.grpc.api.helloworld.GreeterGrpc;
import org.hongxi.whatsmars.grpc.api.helloworld.HelloReply;
import org.hongxi.whatsmars.grpc.api.helloworld.HelloRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.ImportGrpcClients;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ImportGrpcClients(basePackages = "org.hongxi.whatsmars.grpc.api.helloworld")
public class GrpcClientApplication {
	private static final Logger logger = LoggerFactory.getLogger(GrpcClientApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}

	@Bean
	public GreeterGrpc.GreeterStub greeterAsyncStub(GreeterGrpc.GreeterBlockingStub blockingStub) {
		return GreeterGrpc.newStub(blockingStub.getChannel());
	}

	@Bean
	public CommandLineRunner runner(GreeterGrpc.GreeterBlockingStub blockingStub,
									GreeterGrpc.GreeterStub asyncStub) {
		return args -> {
			// 1. Unary
			logger.info("=== Unary ===");
			HelloReply reply = blockingStub.sayHello(
					HelloRequest.newBuilder().setName("Alien").build());
			logger.info("Response: {}", reply.getMessage());

			// 2. Server streaming
			logger.info("=== Server Streaming ===");
			Iterator<HelloReply> replies = blockingStub.sayHelloServerStream(
					HelloRequest.newBuilder().setName("Alien").build());
			replies.forEachRemaining(r -> logger.info("Response: {}", r.getMessage()));

			// 3. Client streaming
			logger.info("=== Client Streaming ===");
			CountDownLatch clientLatch = new CountDownLatch(1);
			StreamObserver<HelloRequest> requestObserver = asyncStub.sayHelloClientStream(
					new StreamObserver<>() {
						@Override
						public void onNext(HelloReply value) {
							logger.info("Response: {}", value.getMessage());
						}

						@Override
						public void onError(Throwable t) {
							logger.error("Error", t);
							clientLatch.countDown();
						}

						@Override
						public void onCompleted() {
							clientLatch.countDown();
						}
					});
			List.of("Alice", "Bob", "Charlie").forEach(name -> {
				requestObserver.onNext(HelloRequest.newBuilder().setName(name).build());
				logger.info("Sent: {}", name);
			});
			requestObserver.onCompleted();
			clientLatch.await();

			// 4. Bidirectional streaming
			logger.info("=== Bidi Streaming ===");
			CountDownLatch bidiLatch = new CountDownLatch(1);
			StreamObserver<HelloRequest> bidiRequestObserver = asyncStub.sayHelloBidiStream(
					new StreamObserver<>() {
						@Override
						public void onNext(HelloReply value) {
							logger.info("Response: {}", value.getMessage());
						}

						@Override
						public void onError(Throwable t) {
							logger.error("Error", t);
							bidiLatch.countDown();
						}

						@Override
						public void onCompleted() {
							bidiLatch.countDown();
						}
					});
			List.of("Dave", "Eve").forEach(name -> {
				bidiRequestObserver.onNext(HelloRequest.newBuilder().setName(name).build());
				logger.info("Sent: {}", name);
			});
			bidiRequestObserver.onCompleted();
			bidiLatch.await();
		};
	}

}