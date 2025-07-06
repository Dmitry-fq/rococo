package ru.rococo.service.impl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rococo.config.Config;
import ru.rococo.grpc.AllMuseumsRequest;
import ru.rococo.grpc.AllMuseumsResponse;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.Geo;
import ru.rococo.grpc.Museum;
import ru.rococo.grpc.Pageable;
import ru.rococo.grpc.RococoMuseumServiceGrpc;
import ru.rococo.model.CountryJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.service.GeoClient;
import ru.rococo.service.MuseumClient;

import javax.annotation.Nullable;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;

public class MuseumGrpcClient implements MuseumClient {

    protected static final Config CFG = Config.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(MuseumGrpcClient.class);

    private final GeoClient geoGrpcClient = new GeoGrpcClient();

    private final RococoMuseumServiceGrpc.RococoMuseumServiceBlockingStub rococoMuseumServiceBlockingStub;

    public MuseumGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(CFG.museumGrpcAddress(), CFG.museumGrpcPort())
                                                      .usePlaintext()
                                                      .build();
        this.rococoMuseumServiceBlockingStub = RococoMuseumServiceGrpc.newBlockingStub(channel);
    }

    @Nullable
    @Override
    public MuseumJson getMuseum(@NotNull String id) {
        try {
            Museum museumResponse = rococoMuseumServiceBlockingStub.getMuseum(
                    Museum.newBuilder()
                          .setId(id)
                          .build()
            );
            return MuseumJson.fromMuseum(museumResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @Nullable
    @Override
    public MuseumJson getMuseumByTitle(@NotNull String name) {
        return allMuseums(name, 0, MAX_VALUE).stream()
                                             .findFirst()
                                             .orElse(null);
    }

    @NotNull
    @Override
    public List<MuseumJson> allMuseums(@NotNull String title, int page, int size) {
        try {
            AllMuseumsResponse museumListResponse = rococoMuseumServiceBlockingStub.allMuseums(
                    AllMuseumsRequest.newBuilder()
                                     .setTitle(title)
                                     .setPageable(
                                             Pageable.newBuilder()
                                                     .setPage(page)
                                                     .setSize(size)
                                                     .build()
                                     )
                                     .build()
            );
            return museumListResponse.getMuseumsList().stream()
                                     .map(MuseumJson::fromMuseum)
                                     .toList();

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public MuseumJson updateMuseum(@NotNull MuseumJson museumJson) {
        try {
            Museum museumResponse = rococoMuseumServiceBlockingStub.updateMuseum(
                    Museum.newBuilder()
                          .setId(String.valueOf(museumJson.id()))
                          .setTitle(museumJson.title())
                          .setDescription(museumJson.description())
                          .setPhoto(museumJson.photo())
                          .setGeo(createGeo(museumJson))
                          .build()
            );
            return MuseumJson.fromMuseum(museumResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public MuseumJson addMuseum(@NotNull MuseumJson museumJson) {
        try {
            Museum museumResponse = rococoMuseumServiceBlockingStub.addMuseum(
                    Museum.newBuilder()
                          .setTitle(museumJson.title())
                          .setDescription(museumJson.description())
                          .setPhoto(museumJson.photo())
                          .setGeo(createGeo(museumJson))
                          .build()
            );
            return MuseumJson.fromMuseum(museumResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    private Geo createGeo(MuseumJson museumJson) {
        final CountryJson countryJson = geoGrpcClient.getCountryByName(museumJson.geo().country().name());

        return Geo.newBuilder()
                  .setCountry(
                          Country.newBuilder()
                                 .setId(String.valueOf(countryJson.id()))
                                 .setName(museumJson.geo().country().name())
                                 .build()
                  )
                  .setCity(museumJson.geo().city())
                  .build();
    }
}
