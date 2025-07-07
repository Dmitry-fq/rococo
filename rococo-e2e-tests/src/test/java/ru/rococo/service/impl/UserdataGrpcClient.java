package ru.rococo.service.impl;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rococo.api.core.GrpcClient;
import ru.rococo.api.core.ThreadSafeCookieStore;
import ru.rococo.config.Config;
import ru.rococo.grpc.RococoUserdataServiceGrpc;
import ru.rococo.grpc.UserRequest;
import ru.rococo.grpc.UserResponse;
import ru.rococo.model.UserJson;
import ru.rococo.service.UserdataClient;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class UserdataGrpcClient extends GrpcClient implements UserdataClient {

    protected static final Config CFG = Config.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(UserdataClient.class);

    private final RococoUserdataServiceGrpc.RococoUserdataServiceBlockingStub rococoUserdataServiceStub;

    private final AuthApiClient authApiClient = new AuthApiClient();

    public UserdataGrpcClient() {
        super(CFG.userdataGrpcAddress(), CFG.userdataGrpcPort());
        this.rococoUserdataServiceStub = RococoUserdataServiceGrpc.newBlockingStub(channel);
    }

    @Nonnull
    @Override
    public UserJson getUser(@Nonnull String username) {
        UserResponse userResponse = rococoUserdataServiceStub.getUser(
                UserRequest.newBuilder()
                           .setUsername(username)
                           .build()
        );
        return UserJson.fromUserResponse(userResponse);
    }

    @Nonnull
    @Override
    public UserJson createUser(@Nonnull String username, @Nonnull String password) {
        try {
            authApiClient.getRegisterPage();
            authApiClient.registerUser(
                    username,
                    password,
                    password,
                    ThreadSafeCookieStore.INSTANCE.cookieValue("XSRF-TOKEN")
            );
            int maxWaitTime = 3000;
            Stopwatch sw = Stopwatch.createStarted();
            while (sw.elapsed(TimeUnit.MILLISECONDS) < maxWaitTime) {
                UserJson userJson = getUser(username);
                if (userJson.id() != null) {
                    return userJson;
                } else {
                    sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throw new AssertionError("Пользователь " + username + " не был найден за отведённое время");
    }
}
