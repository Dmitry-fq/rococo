package ru.rococo.config;

import javax.annotation.Nonnull;

public interface Config {

    static @Nonnull Config getInstance() {
        return LocalConfig.INSTANCE;
    }

    @Nonnull
    String frontUrl();

    @Nonnull
    String authUrl();

    @Nonnull
    String gatewayUrl();

    @Nonnull
    String userdataUrl();

    @Nonnull
    String userdataJdbcUrl();
}
