package com.gsaikiran.grpc.interceptors;

import io.grpc.*;
import io.grpc.ServerCall.Listener;

import java.util.Date;

public class DebugInterceptor implements ServerInterceptor {


    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
                                                      ServerCallHandler<ReqT, RespT> next) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
                next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                    @Override
                    public void sendMessage(RespT message) {
                        System.out.print("Response: " + message);
                        super.sendMessage(message);
                    }

                    @Override
                    public void close(Status status, Metadata trailers) {
                        System.out.println("Response code: " + status.getCode().toString()+"\n");
                        super.close(status, trailers);

                    }
                }, headers)) {

            @Override
            public void onMessage(ReqT message) {
                System.out.println("Request: " + message + " | Time: " + new Date());
                super.onMessage(message);
            }

        };
    }
}
