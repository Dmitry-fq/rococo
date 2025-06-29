package ru.rococo.service.impl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.openqa.selenium.NotFoundException;
import ru.rococo.config.Config;
import ru.rococo.grpc.RococoUserdataServiceGrpc;
import ru.rococo.grpc.UserRequest;
import ru.rococo.grpc.UserResponse;
import ru.rococo.model.UserJson;

import javax.annotation.Nonnull;

public class UserdataGrpcClient {

    protected static final Config CFG = Config.getInstance();

    private final RococoUserdataServiceGrpc.RococoUserdataServiceBlockingStub rococoUserdataServiceStub;

    private final ManagedChannel channel;

    public UserdataGrpcClient() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8092)
                                            .usePlaintext()
                                            .build();
        this.rococoUserdataServiceStub = RococoUserdataServiceGrpc.newBlockingStub(channel);
    }

    @Nonnull
    public UserJson getUser(String username) {
        try {
            UserResponse userResponse = rococoUserdataServiceStub.getUser(
                    UserRequest.newBuilder()
                               .setUsername(username)
                               .build()
            );
            return UserJson.fromUserResponse(userResponse);

        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }
}
