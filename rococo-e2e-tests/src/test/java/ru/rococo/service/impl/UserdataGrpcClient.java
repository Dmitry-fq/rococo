package ru.rococo.service.impl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.openqa.selenium.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rococo.config.Config;
import ru.rococo.grpc.RococoUserdataServiceGrpc;
import ru.rococo.grpc.UserRequest;
import ru.rococo.grpc.UserResponse;
import ru.rococo.model.UserJson;
import ru.rococo.service.UserdataClient;

import javax.annotation.Nonnull;

public class UserdataGrpcClient implements UserdataClient {

    protected static final Config CFG = Config.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(UserdataClient.class);

    private final RococoUserdataServiceGrpc.RococoUserdataServiceBlockingStub rococoUserdataServiceStub;

    public UserdataGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(CFG.userdataGrpcAddress(), CFG.userdataGrpcPort())
                                                      .usePlaintext()
                                                      .build();
        this.rococoUserdataServiceStub = RococoUserdataServiceGrpc.newBlockingStub(channel);
    }

    @Nonnull
    @Override
    public UserJson getUser(@Nonnull String username) {
        try {
            UserResponse userResponse = rococoUserdataServiceStub.getUser(
                    UserRequest.newBuilder()
                               .setUsername(username)
                               .build()
            );
            return UserJson.fromUserResponse(userResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }
}
