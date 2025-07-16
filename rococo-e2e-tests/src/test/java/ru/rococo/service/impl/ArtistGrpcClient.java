package ru.rococo.service.impl;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rococo.api.core.GrpcClient;
import ru.rococo.config.Config;
import ru.rococo.grpc.AllArtistsRequest;
import ru.rococo.grpc.Artist;
import ru.rococo.grpc.ArtistsResponse;
import ru.rococo.grpc.Pageable;
import ru.rococo.grpc.RococoArtistServiceGrpc;
import ru.rococo.model.ArtistJson;
import ru.rococo.service.ArtistClient;
import ru.rococo.service.UserdataClient;

import javax.annotation.Nullable;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;

public class ArtistGrpcClient extends GrpcClient implements ArtistClient {

    protected static final Config CFG = Config.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(UserdataClient.class);

    private final RococoArtistServiceGrpc.RococoArtistServiceBlockingStub rococoArtistServiceStub;

    public ArtistGrpcClient() {
        super(CFG.artistGrpcAddress(), CFG.artistGrpcPort());
        this.rococoArtistServiceStub = RococoArtistServiceGrpc.newBlockingStub(channel);
    }

    @NotNull
    @Override
    public ArtistJson getArtist(@NotNull String id) {
        Artist artistResponse = rococoArtistServiceStub.getArtist(
                Artist.newBuilder()
                      .setId(id)
                      .build()
        );
        return ArtistJson.fromArtist(artistResponse);
    }

    @Nullable
    @Override
    public ArtistJson getArtistByName(@NotNull String name) {
        return allArtists(name, 0, MAX_VALUE).stream()
                                             .findFirst()
                                             .orElse(null);
    }

    @Override
    public List<ArtistJson> allArtists(String name, int page, int size) {
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
    }

    @NotNull
    @Override
    public ArtistJson updateArtist(@NotNull ArtistJson artistJson) {
        Artist artistResponse = rococoArtistServiceStub.updateArtist(
                Artist.newBuilder()
                      .setId(String.valueOf(artistJson.id()))
                      .setName(artistJson.name())
                      .setBiography(artistJson.biography())
                      .setPhoto(artistJson.photo())
                      .build()
        );
        return ArtistJson.fromArtist(artistResponse);
    }

    @NotNull
    @Override
    public ArtistJson addArtist(@NotNull ArtistJson artistJson) {
        Artist artistResponse = rococoArtistServiceStub.addArtist(
                Artist.newBuilder()
                      .setName(artistJson.name())
                      .setBiography(artistJson.biography())
                      .setPhoto(artistJson.photo())
                      .build()
        );
        return ArtistJson.fromArtist(artistResponse);
    }
}
