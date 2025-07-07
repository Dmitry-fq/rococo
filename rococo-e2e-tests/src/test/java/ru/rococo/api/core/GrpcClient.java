package ru.rococo.api.core;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.rococo.utils.GrpcConsoleInterceptor;

public class GrpcClient {

    protected ManagedChannel channel;

    protected GrpcClient(String address, int port) {
        channel = ManagedChannelBuilder.forAddress(address, port)
                                       .intercept(new GrpcConsoleInterceptor())
                                       .usePlaintext()
                                       .build();
    }
}
