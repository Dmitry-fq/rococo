package ru.rococo.service.impl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rococo.config.Config;
import ru.rococo.grpc.AllArtistsRequest;
import ru.rococo.grpc.Artist;
import ru.rococo.grpc.ArtistsResponse;
import ru.rococo.grpc.Pageable;
import ru.rococo.grpc.RococoArtistServiceGrpc;
import ru.rococo.model.ArtistJson;
import ru.rococo.service.ArtistClient;
import ru.rococo.service.UserdataClient;

import java.util.List;

public class ArtistGrpcClient implements ArtistClient {

    protected static final Config CFG = Config.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(UserdataClient.class);

    private final RococoArtistServiceGrpc.RococoArtistServiceBlockingStub rococoArtistServiceStub;

    public ArtistGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(CFG.artistGrpcAddress(), CFG.artistGrpcPort())
                                                      .usePlaintext()
                                                      .build();
        this.rococoArtistServiceStub = RococoArtistServiceGrpc.newBlockingStub(channel);
    }

    @NotNull
    @Override
    public ArtistJson getArtist(@NotNull String id) {
        try {
            Artist artistResponse = rococoArtistServiceStub.getArtist(
                    Artist.newBuilder()
                          .setId(id)
                          .build()
            );
            return ArtistJson.fromArtist(artistResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public List<ArtistJson> allArtists(@NotNull String name, int page, int size) {
        try {
            ArtistsResponse artistListResponse = rococoArtistServiceStub.allArtists(
                    AllArtistsRequest.newBuilder()
                                     .setName(name)
                                     .setPageable(
                                             Pageable.newBuilder()
                                                     .setPage(page)
                                                     .setSize(size)
                                                     .build()
                                     )
                                     .build()
            );
            return artistListResponse.getArtistListList().stream()
                                     .map(ArtistJson::fromArtist)
                                     .toList();

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public ArtistJson updateArtist(@NotNull ArtistJson artistJson) {
        try {
            Artist artistResponse = rococoArtistServiceStub.updateArtist(
                    Artist.newBuilder()
                          .setId(String.valueOf(artistJson.id()))
                          .setName(artistJson.name())
                          .setBiography(artistJson.biography())
                          .setPhoto(artistJson.photo())
                          .build()
            );
            return ArtistJson.fromArtist(artistResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public ArtistJson addArtist(@NotNull ArtistJson artistJson) {
        try {
            Artist artistResponse = rococoArtistServiceStub.addArtist(
                    Artist.newBuilder()
                          .setName(artistJson.name())
                          .setBiography(artistJson.biography())
                          .setPhoto(artistJson.photo())
                          .build()
            );
            return ArtistJson.fromArtist(artistResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }
}
