package ru.rococo.config;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public enum LocalConfig implements Config {

    INSTANCE;

    @Nonnull
    @Override
    public String frontUrl() {
        return "http://127.0.0.1:3000/";
    }

    @Nonnull
    @Override
    public String authUrl() {
        return "http://127.0.0.1:9000/";
    }

    @NotNull
    @Override
    public String gatewayUrl() {
        return "http://127.0.0.1:8080/";
    }

    @NotNull
    @Override
    public String userdataUrl() {
        return "http://127.0.0.1:8091/";
    }

    @Nonnull
    @Override
    public String userdataJdbcUrl() {
        return "jdbc:postgresql://127.0.0.1:5432/rococo-userdata";
    }
}
