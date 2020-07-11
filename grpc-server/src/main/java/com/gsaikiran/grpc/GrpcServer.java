package com.gsaikiran.grpc;

import com.gsaikiran.grpc.interceptors.DebugInterceptor;
import com.gsaikiran.grpc.service.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.
                forPort(6090)
                .intercept(new DebugInterceptor())
                .addService(new HelloServiceImpl())
                .build()
                .start();

        System.out.println("Server started");
        server.awaitTermination();

    }
}
