package ru.rococo.service.impl;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rococo.api.core.GrpcClient;
import ru.rococo.config.Config;
import ru.rococo.grpc.AllCountriesResponse;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.Pageable;
import ru.rococo.grpc.RococoGeoServiceGrpc;
import ru.rococo.model.CountryJson;
import ru.rococo.service.GeoClient;

import java.util.List;

public class GeoGrpcClient extends GrpcClient implements GeoClient {

    protected static final Config CFG = Config.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(GeoGrpcClient.class);

    private final RococoGeoServiceGrpc.RococoGeoServiceBlockingStub rococoGeoServiceBlockingStub;

    public GeoGrpcClient() {
        super(CFG.geoGrpcAddress(), CFG.geoGrpcPort());
        this.rococoGeoServiceBlockingStub = RococoGeoServiceGrpc.newBlockingStub(channel);
    }

    @NotNull
    @Override
    public CountryJson getCountry(@NotNull String id) {
        Country countryResponse = rococoGeoServiceBlockingStub.getCountry(
                Country.newBuilder()
                       .setId(id)
                       .build()
        );
        return CountryJson.fromCountry(countryResponse);
    }

    @NotNull
    @Override
    public CountryJson getCountryByName(@NotNull String name) {
        Country countryResponse = rococoGeoServiceBlockingStub.getCountryByName(
                Country.newBuilder()
                       .setName(name)
                       .build()
        );
        return CountryJson.fromCountry(countryResponse);
    }

    @NotNull
    @Override
    public List<CountryJson> allCountries(int page, int size) {
        AllCountriesResponse countryListResponse = rococoGeoServiceBlockingStub.allCountries(
                Pageable.newBuilder()
                        .setPage(page)
                        .setSize(size)
                        .build()
        );
        return countryListResponse.getCountriesList().stream()
                                  .map(CountryJson::fromCountry)
                                  .toList();
    }
}
