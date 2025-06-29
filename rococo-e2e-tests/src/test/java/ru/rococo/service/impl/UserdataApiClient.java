package ru.rococo.service.impl;

import com.google.common.base.Stopwatch;
import org.jetbrains.annotations.NotNull;
import ru.rococo.api.UserdataApi;
import ru.rococo.api.core.RestClient;
import ru.rococo.api.core.ThreadSafeCookieStore;
import ru.rococo.model.UserJson;
import ru.rococo.service.UsersClient;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class UserdataApiClient extends RestClient implements UsersClient {

    private final UserdataApi userdataApi;

    private final UserdataGrpcClient userdataGrpcClient = new UserdataGrpcClient();

    private final AuthApiClient authApiClient = new AuthApiClient();

    public UserdataApiClient() {
        super(CFG.gatewayUrl());
        this.userdataApi = retrofit.create(UserdataApi.class);
    }

    @Nonnull
    @Override
    public UserJson createUser(@NotNull String username, @NotNull String password) {
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
                String token = authApiClient.login(username, password);
//                UserJson userJson = userdataApi.user("Bearer " + token).execute().body();
                UserJson userJson = userdataGrpcClient.getUser(username);
                if (userJson != null && userJson.id() != null) {
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
