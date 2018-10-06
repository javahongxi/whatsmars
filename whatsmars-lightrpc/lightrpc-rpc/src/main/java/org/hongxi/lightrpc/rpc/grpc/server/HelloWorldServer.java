package org.hongxi.lightrpc.rpc.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.hongxi.lightrpc.rpc.grpc.service.HelloServiceGrpc;
import org.hongxi.lightrpc.rpc.grpc.service.HelloRequest;
import org.hongxi.lightrpc.rpc.grpc.service.HelloResponse;

import java.io.IOException;

/**
 * Created by shenhongxi on 2017/5/5.
 */
public class HelloWorldServer {

    private int port = 50051;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new HelloServiceImpl())
                .build()
                .start();

        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorldServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block 一直到退出程序 
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUntilShutdown();
    }

    // 实现 定义一个实现服务接口的类 
    private class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

        public void sayHello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
            System.out.println("service:" + req.getName());
            HelloResponse response = HelloResponse.newBuilder().setMessage(("Hello: " + req.getName())).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
