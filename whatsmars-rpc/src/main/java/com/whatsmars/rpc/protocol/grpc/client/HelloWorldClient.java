package com.whatsmars.rpc.protocol.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.whatsmars.grpc.service.HelloServiceGrpc;
import com.whatsmars.grpc.service.HelloRequest;
import com.whatsmars.grpc.service.HelloResponse;

import java.util.concurrent.TimeUnit;

/**
 * Created by shenhongxi on 2017/5/5.
 */
public class HelloWorldClient {

    private final ManagedChannel channel;
    private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

    public HelloWorldClient(String host,int port){
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();

        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloResponse response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClient client = new HelloWorldClient("127.0.0.1", 50051);
        for(int i = 0; i < 5; i++) {
            client.greet("world:" + i);
        }
        client.shutdown();
    }
}
