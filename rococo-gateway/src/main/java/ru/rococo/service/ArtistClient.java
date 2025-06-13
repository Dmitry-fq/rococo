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
import ru.rococo.grpc.AllArtistsRequest;
import ru.rococo.grpc.ArtistListResponse;
import ru.rococo.grpc.ArtistRequest;
import ru.rococo.grpc.ArtistResponse;
import ru.rococo.grpc.RococoArtistServiceGrpc;
import ru.rococo.model.ArtistJson;

import java.util.List;

@Component
public class ArtistClient {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistClient.class);

    @GrpcClient("artistClient")
    private RococoArtistServiceGrpc.RococoArtistServiceBlockingStub rococoArtistServiceStub;

    public @Nonnull ArtistJson getArtist(String id) {
        try {
            ArtistResponse artistResponse = rococoArtistServiceStub.getArtist(
                    ArtistRequest.newBuilder()
                                 .setId(id)
                                 .build()
            );
            return ArtistJson.fromArtistResponse(artistResponse);

        } catch (StatusRuntimeException e) {

            //TODO в константу
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public Page<ArtistJson> allArtists(String name, @Nonnull Pageable pageable) {
        try {
            ArtistListResponse artistListResponse = rococoArtistServiceStub.allArtists(
                    AllArtistsRequest.newBuilder()
                                     .setName(name)
                                     .setPageable(
                                             ru.rococo.grpc.Pageable.newBuilder()
                                                                    .setPage(pageable.getPageNumber())
                                                                    .setSize(pageable.getPageSize())
                                                                    .build()
                                     )
                                     .build()

            );

            List<ArtistJson> artistJsonList = artistListResponse.getArtistListList().stream()
                                                                .map(ArtistJson::fromArtistResponse)
                                                                .toList();

            return new PageImpl<>(artistJsonList, pageable, artistListResponse.getTotalCount());

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull ArtistJson updateArtist(ArtistJson artistJson) {
        try {
            ArtistResponse artistResponse = rococoArtistServiceStub.updateArtist(
                    ArtistRequest.newBuilder()
                                 .setId(String.valueOf(artistJson.id()))
                                 .setName(artistJson.name())
                                 .setBiography(artistJson.biography())
                                 .setPhoto(artistJson.photo())
                                 .build()
            );

            return ArtistJson.fromArtistResponse(artistResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull ArtistJson addArtist(ArtistJson artistJson) {
        try {
            ArtistResponse artistResponse = rococoArtistServiceStub.addArtist(
                    ArtistRequest.newBuilder()
                                 .setName(artistJson.name())
                                 .setBiography(artistJson.biography())
                                 .setPhoto(artistJson.photo())
                                 .build()
            );

            return ArtistJson.fromArtistResponse(artistResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }
}
