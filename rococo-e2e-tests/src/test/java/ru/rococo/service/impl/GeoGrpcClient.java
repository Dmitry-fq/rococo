package ru.rococo.service.impl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rococo.config.Config;
import ru.rococo.grpc.AllCountriesResponse;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.Pageable;
import ru.rococo.grpc.RococoGeoServiceGrpc;
import ru.rococo.model.CountryJson;
import ru.rococo.service.GeoClient;

import java.util.List;

public class GeoGrpcClient implements GeoClient {

    protected static final Config CFG = Config.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(GeoGrpcClient.class);

    private final RococoGeoServiceGrpc.RococoGeoServiceBlockingStub rococoGeoServiceBlockingStub;

    public GeoGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(CFG.geoGrpcAddress(), CFG.geoGrpcPort())
                                                      .usePlaintext()
                                                      .build();
        this.rococoGeoServiceBlockingStub = RococoGeoServiceGrpc.newBlockingStub(channel);
    }

    @NotNull
    @Override
    public CountryJson getCountry(@NotNull String id) {
        try {
            Country countryResponse = rococoGeoServiceBlockingStub.getCountry(
                    Country.newBuilder()
                           .setId(id)
                           .build()
            );
            return CountryJson.fromCountry(countryResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public CountryJson getCountryByName(@NotNull String name) {
        try {
            Country countryResponse = rococoGeoServiceBlockingStub.getCountryByName(
                    Country.newBuilder()
                           .setName(name)
                           .build()
            );
            return CountryJson.fromCountry(countryResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public List<CountryJson> allCountries(int page, int size) {
        try {
            AllCountriesResponse countryListResponse = rococoGeoServiceBlockingStub.allCountries(
                    Pageable.newBuilder()
                            .setPage(page)
                            .setSize(size)
                            .build()
            );
            return countryListResponse.getCountriesList().stream()
                                      .map(CountryJson::fromCountry)
                                      .toList();

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }
}
