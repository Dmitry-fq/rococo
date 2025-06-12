package ru.rococo.service;

import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.rococo.grpc.RococoUserdataServiceGrpc;
import ru.rococo.grpc.UserRequest;
import ru.rococo.grpc.UserResponse;
import ru.rococo.model.UserJson;

@Component
public class UserdataClient {

    private static final Logger LOG = LoggerFactory.getLogger(UserdataClient.class);

    @GrpcClient("userdataClient")
    private RococoUserdataServiceGrpc.RococoUserdataServiceBlockingStub rococoUserdataServiceStub;

    public @Nonnull UserJson getUser(String username) {
        try {
            UserResponse userResponse = rococoUserdataServiceStub.getUser(
                    UserRequest.newBuilder()
                               .setUsername(username)
                               .build()
            );
            return UserJson.fromUserResponse(userResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull UserJson updateUser(UserJson userJson) {
        try {
            UserResponse userResponse = rococoUserdataServiceStub.updateUser(
                    UserRequest.newBuilder()
                               .setUsername(userJson.username())
                               .setFirstname(userJson.firstname())
                               .setLastname(userJson.lastname())
                               .setAvatar(userJson.avatar())
                               .build()
            );

            return UserJson.fromUserResponse(userResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }
}
