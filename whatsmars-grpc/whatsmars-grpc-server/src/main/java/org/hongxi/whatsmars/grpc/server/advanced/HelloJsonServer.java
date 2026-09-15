package org.hongxi.whatsmars.grpc.server.advanced;

import static io.grpc.stub.ServerCalls.asyncUnaryCall;

import io.grpc.*;
import io.grpc.stub.AbstractStub;
import org.hongxi.whatsmars.grpc.api.helloworld.GreeterGrpc;
import org.hongxi.whatsmars.grpc.api.helloworld.HelloReply;
import org.hongxi.whatsmars.grpc.api.helloworld.HelloRequest;
import io.grpc.stub.ServerCalls.UnaryMethod;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Server that manages startup/shutdown of a {@code Greeter} server.
 *
 * <p>This is an advanced example of how to swap out the serialization logic.  Normal users do not
 * need to do this.  This code is not intended to be a production-ready implementation, since JSON
 * encoding is slow.  Additionally, JSON serialization as implemented may be not resilient to
 * malicious input.
 *
 * <p>If you are considering implementing your own serialization logic, contact the grpc team at
 * https://groups.google.com/forum/#!forum/grpc-io
 */
public class HelloJsonServer {
    private static final Logger logger = LoggerFactory.getLogger(HelloJsonServer.class);

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("Server started, listening on {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                logger.info("Shutting down gRPC server since JVM is shutting down");
                try {
                    HelloJsonServer.this.stop();
                } catch (InterruptedException e) {
                    logger.error("Error during server shutdown", e);
                }
                logger.info("Server shut down");
            }
        });
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloJsonServer server = new HelloJsonServer();
        server.start();
        server.blockUntilShutdown();
    }

    private static class GreeterImpl implements BindableService {

        private void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition
                    .builder(GreeterGrpc.getServiceDescriptor().getName())
                    .addMethod(HelloJsonStub.METHOD_SAY_HELLO,
                            asyncUnaryCall(
                                    new UnaryMethod<HelloRequest, HelloReply>() {
                                        @Override
                                        public void invoke(
                                                HelloRequest request, StreamObserver<HelloReply> responseObserver) {
                                            sayHello(request, responseObserver);
                                        }
                                    }))
                    .build();
        }

        static final class HelloJsonStub extends AbstractStub<HelloJsonStub> {

            static final MethodDescriptor<HelloRequest, HelloReply> METHOD_SAY_HELLO =
                    GreeterGrpc.getSayHelloMethod()
                            .toBuilder(
                                    JsonMarshaller.jsonMarshaller(HelloRequest.getDefaultInstance()),
                                    JsonMarshaller.jsonMarshaller(HelloReply.getDefaultInstance()))
                            .build();

            private HelloJsonStub(Channel channel, CallOptions callOptions) {
                super(channel, callOptions);
            }

            @Override
            protected HelloJsonStub build(Channel channel, CallOptions callOptions) {
                return new HelloJsonStub(channel, callOptions);
            }
        }
    }
}