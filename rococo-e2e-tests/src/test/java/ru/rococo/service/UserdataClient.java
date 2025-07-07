package ru.rococo.service;

import ru.rococo.model.UserJson;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface UserdataClient {

    @Nonnull
    UserJson getUser(String username);

    @Nonnull
    UserJson createUser(@Nonnull String username, @Nonnull String password);
}
