package ru.rococo.config;

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

    @Nonnull
    @Override
    public String gatewayUrl() {
        return "http://127.0.0.1:8080/";
    }

    @Nonnull
    @Override
    public String userdataUrl() {
        return "http://127.0.0.1:8091/";
    }

    @Nonnull
    @Override
    public String userdataGrpcAddress() {
        return "127.0.0.1";
    }

    @Nonnull
    @Override
    public String userdataJdbcUrl() {
        return "jdbc:postgresql://127.0.0.1:5432/rococo-userdata";
    }

    @Nonnull
    @Override
    public String artistUrl() {
        return "http://127.0.0.1:8093/";
    }

    @Nonnull
    @Override
    public String artistGrpcAddress() {
        return "127.0.0.1";
    }

    @Nonnull
    @Override
    public String artistJdbcUrl() {
        return "jdbc:postgresql://127.0.0.1:5432/rococo-artist";
    }

    @Nonnull
    @Override
    public String geoUrl() {
        return "http://127.0.0.1:8095/";
    }

    @Nonnull
    @Override
    public String geoGrpcAddress() {
        return "127.0.0.1";
    }

    @Nonnull
    @Override
    public String geoJdbcUrl() {
        return "jdbc:postgresql://127.0.0.1:5432/rococo-geo";
    }

    @Nonnull
    @Override
    public String museumUrl() {
        return "http://127.0.0.1:8097/";
    }

    @Nonnull
    @Override
    public String museumGrpcAddress() {
        return "127.0.0.1";
    }

    @Nonnull
    @Override
    public String museumJdbcUrl() {
        return "jdbc:postgresql://127.0.0.1:5432/rococo-museum";
    }

    @Nonnull
    @Override
    public String paintingUrl() {
        return "http://127.0.0.1:8099/";
    }

    @Nonnull
    @Override
    public String paintingGrpcAddress() {
        return "127.0.0.1";
    }

    @Nonnull
    @Override
    public String paintingJdbcUrl() {
        return "jdbc:postgresql://127.0.0.1:5432/rococo-painting";
    }
}
