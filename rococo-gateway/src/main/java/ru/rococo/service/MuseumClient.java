package ru.rococo.service;

import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.rococo.grpc.AllMuseumsRequest;
import ru.rococo.grpc.AllMuseumsResponse;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.Geo;
import ru.rococo.grpc.Museum;
import ru.rococo.grpc.RococoMuseumServiceGrpc;
import ru.rococo.model.MuseumJson;

import java.util.List;

@Component
public class MuseumClient {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumClient.class);

    @GrpcClient("museumClient")
    private RococoMuseumServiceGrpc.RococoMuseumServiceBlockingStub rococoMuseumServiceStub;

    public @Nonnull MuseumJson getMuseum(@Nonnull String id) {
        try {
            Museum museumResponse = rococoMuseumServiceStub.getMuseum(
                    Museum.newBuilder()
                          .setId(id)
                          .build()
            );
            return MuseumJson.fromMuseum(museumResponse);

        } catch (StatusRuntimeException e) {

            //TODO в константу
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public Page<MuseumJson> allMuseums(String title, @Nonnull Pageable pageable) {
        try {
            AllMuseumsResponse museumsResponse = rococoMuseumServiceStub.allMuseums(
                    AllMuseumsRequest.newBuilder()
                                     .setTitle(title)
                                     .setPageable(
                                             ru.rococo.grpc.Pageable.newBuilder()
                                                                    .setPage(pageable.getPageNumber())
                                                                    .setSize(pageable.getPageSize())
                                                                    .build()
                                     )
                                     .build()

            );

            List<MuseumJson> museumList = museumsResponse.getMuseumsList().stream()
                                                         .map(MuseumJson::fromMuseum)
                                                         .toList();

            return new PageImpl<>(museumList, pageable, museumsResponse.getTotalCount());

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull MuseumJson updateMuseum(@Nonnull MuseumJson museumJson) {
        try {
            Museum museumResponse = rococoMuseumServiceStub.updateMuseum(
                    Museum.newBuilder()
                          .setId(String.valueOf(museumJson.id()))
                          .setTitle(museumJson.title())
                          .setDescription(museumJson.description())
                          .setPhoto(museumJson.photo())
                          .setGeo(Geo.newBuilder()
                                     .setCountry(Country.newBuilder()
                                                        .setId(String.valueOf(museumJson.geo().country().id()))
                                                        .build()
                                     )
                                     .setCity(museumJson.geo().city())
                          ).build()
            );
            return MuseumJson.fromMuseum(museumResponse);

        } catch (StatusRuntimeException e) {

            //TODO в константу
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull MuseumJson addMuseum(@Nonnull MuseumJson museumJson) {
        try {
            Museum museumResponse = rococoMuseumServiceStub.addMuseum(
                    Museum.newBuilder()
                          .setId(String.valueOf(museumJson.id()))
                          .setTitle(museumJson.title())
                          .setDescription(museumJson.description())
                          .setPhoto(museumJson.photo())
                          .setGeo(Geo.newBuilder()
                                     .setCountry(Country.newBuilder()
                                                        .setId(String.valueOf(museumJson.geo().country().id()))
                                                        .build()
                                     )
                                     .setCity(museumJson.geo().city())
                          ).build()
            );
            return MuseumJson.fromMuseum(museumResponse);

        } catch (StatusRuntimeException e) {

            //TODO в константу
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }
}
