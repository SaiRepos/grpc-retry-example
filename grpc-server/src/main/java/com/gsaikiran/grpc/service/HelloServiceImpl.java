package com.gsaikiran.grpc.service;


import com.gsaikiran.HelloGrpc;
import com.gsaikiran.HelloRequest;
import com.gsaikiran.HelloResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Date;

public class HelloServiceImpl extends HelloGrpc.HelloImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseStreamObserver) {
        if (request.getMessage().equals("Hello")) {
            responseStreamObserver.onNext(HelloResponse.newBuilder().setMessage("Hey").build());
            responseStreamObserver.onCompleted();
        } else {
            responseStreamObserver.onError(Status.UNAVAILABLE.asRuntimeException());
        }

    }

}
