package com.itlong.whatsmars.grpc.service.impl;

import com.itlong.whatsmars.grpc.service.HelloRequest;
import com.itlong.whatsmars.grpc.service.HelloResponse;
import com.itlong.whatsmars.grpc.service.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by shenhongxi on 2017/5/5.
 */
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    public void sayHello(HelloRequest req, StreamObserver<HelloResponse> responseObserver) {
        System.out.println("service:" + req.getName());
        HelloResponse response = HelloResponse.newBuilder().setMessage(("Hello: " + req.getName())).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
