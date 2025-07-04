package ru.rococo.service.impl;

import com.google.common.base.Stopwatch;
import ru.rococo.api.core.ThreadSafeCookieStore;
import ru.rococo.model.UserJson;
import ru.rococo.service.UsersClient;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class UserdataApiClient implements UsersClient {

    private final UserdataGrpcClient userdataGrpcClient = new UserdataGrpcClient();

    private final AuthApiClient authApiClient = new AuthApiClient();

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
                UserJson userJson = userdataGrpcClient.getUser(username);
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
