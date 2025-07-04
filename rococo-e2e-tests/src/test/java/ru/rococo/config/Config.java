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
    String userdataGrpcAddress();

    default int userdataGrpcPort() {
        return 8092;
    }

    @Nonnull
    String userdataJdbcUrl();

    @Nonnull
    String artistUrl();

    @Nonnull
    String artistGrpcAddress();

    default int artistGrpcPort() {
        return 8094;
    }

    @Nonnull
    String artistJdbcUrl();

    @Nonnull
    String geoUrl();

    @Nonnull
    String geoGrpcAddress();

    default int geoGrpcPort() {
        return 8096;
    }

    @Nonnull
    String geoJdbcUrl();

    @Nonnull
    String museumUrl();

    @Nonnull
    String museumGrpcAddress();

    default int museumGrpcPort() {
        return 8098;
    }

    @Nonnull
    String museumJdbcUrl();

    @Nonnull
    String paintingUrl();

    @Nonnull
    String paintingGrpcAddress();

    default int paintingGrpcPort() {
        return 8100;
    }

    @Nonnull
    String paintingJdbcUrl();
}
