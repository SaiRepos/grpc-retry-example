package com.gsaikiran.grpc;

import com.gsaikiran.HelloGrpc;
import com.gsaikiran.HelloRequest;
import com.gsaikiran.HelloResponse;
import com.gsaikiran.grpc.interceptors.DebugInterceptor;
import io.grpc.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GrpcRetryClient {

    public static void main(String[] args) throws InterruptedException {


        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6090)
                .defaultServiceConfig(getServiceConfig())
                .enableRetry()
                .usePlaintext()
                .intercept(new DebugInterceptor()).build();


        HelloGrpc.HelloBlockingStub stub = HelloGrpc.newBlockingStub(channel);
        HelloResponse response = stub.sayHello(HelloRequest.newBuilder().setMessage("Hello").build());
        response = stub.sayHello(HelloRequest.newBuilder().setMessage("Retrying").build());
        channel.awaitTermination(20, TimeUnit.SECONDS);
    }

    static Map<String, Object> getServiceConfig() {
        Map<String, Object> retryPolicy = new HashMap<>();
        retryPolicy.put("maxAttempts", 5D);
        retryPolicy.put("initialBackoff", "2s");
        retryPolicy.put("maxBackoff", "60s");
        retryPolicy.put("backoffMultiplier", 3D);
        retryPolicy.put("retryableStatusCodes", Arrays.<Object>asList(Status.UNAVAILABLE.getCode().toString()));
        Map<String, Object> methodConfig = new HashMap<>();
        Map<String, Object> name = new HashMap<>();
        name.put("service", "com.gsaikiran.Hello");
        name.put("method", "SayHello");
        methodConfig.put("name", Collections.<Object>singletonList(name));
        methodConfig.put("retryPolicy", retryPolicy);
        //methodConfig.put("waitForReady",false);
        Map<String, Object> serviceConfig = new HashMap<>();
        serviceConfig.put("methodConfig", Collections.<Object>singletonList(methodConfig));
        return serviceConfig;
    }

}
