package ru.rococo.service.impl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rococo.config.Config;
import ru.rococo.grpc.AllPaintingsByAuthorIdRequest;
import ru.rococo.grpc.AllPaintingsRequest;
import ru.rococo.grpc.AllPaintingsResponse;
import ru.rococo.grpc.Artist;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.Geo;
import ru.rococo.grpc.Museum;
import ru.rococo.grpc.Pageable;
import ru.rococo.grpc.Painting;
import ru.rococo.grpc.RococoPaintingServiceGrpc;
import ru.rococo.model.ArtistJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.model.PaintingJson;
import ru.rococo.service.PaintingClient;

import java.util.List;

public class PaintingGrpcClient implements PaintingClient {

    protected static final Config CFG = Config.getInstance();

    private static final Logger LOG = LoggerFactory.getLogger(PaintingGrpcClient.class);

    private final RococoPaintingServiceGrpc.RococoPaintingServiceBlockingStub rococoPaintingServiceBlockingStub;

    public PaintingGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(CFG.paintingGrpcAddress(), CFG.paintingGrpcPort())
                                                      .usePlaintext()
                                                      .build();
        this.rococoPaintingServiceBlockingStub = RococoPaintingServiceGrpc.newBlockingStub(channel);
    }

    @NotNull
    @Override
    public PaintingJson getPainting(@NotNull String id) {
        try {
            Painting paintingResponse = rococoPaintingServiceBlockingStub.getPainting(
                    Painting.newBuilder()
                            .setId(id)
                            .build()
            );
            return PaintingJson.fromPainting(paintingResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public List<PaintingJson> allPaintings(@NotNull String title, int page, int size) {
        try {
            AllPaintingsResponse paintingListResponse = rococoPaintingServiceBlockingStub.allPaintings(
                    AllPaintingsRequest.newBuilder()
                                       .setTitle(title)
                                       .setPageable(
                                               Pageable.newBuilder()
                                                       .setPage(page)
                                                       .setSize(size)
                                                       .build()
                                       )
                                       .build()
            );
            return paintingListResponse.getPaintingsList().stream()
                                       .map(PaintingJson::fromPainting)
                                       .toList();

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public List<PaintingJson> allPaintingsByAuthorId(@NotNull String authorId, int page, int size) {
        try {
            AllPaintingsResponse paintingListResponse = rococoPaintingServiceBlockingStub.allPaintingsByAuthorId(
                    AllPaintingsByAuthorIdRequest.newBuilder()
                                                 .setAuthorId(authorId)
                                                 .setPageable(
                                                         Pageable.newBuilder()
                                                                 .setPage(page)
                                                                 .setSize(size)
                                                                 .build()
                                                 )
                                                 .build()
            );
            return paintingListResponse.getPaintingsList().stream()
                                       .map(PaintingJson::fromPainting)
                                       .toList();

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public PaintingJson updatePainting(@NotNull PaintingJson paintingJson) {
        try {
            Painting paintingResponse = rococoPaintingServiceBlockingStub.updatePainting(
                    Painting.newBuilder()
                            .setId(String.valueOf(paintingJson.id()))
                            .setArtist(
                                    Artist.newBuilder()
                                          .setId(String.valueOf(paintingJson.artist().id()))
                                          .build()
                            )
                            .setContent(paintingJson.content())
                            .setTitle(paintingJson.title())
                            .setDescription(paintingJson.description())
                            .setMuseum(
                                    Museum.newBuilder()
                                          .setId(String.valueOf(paintingJson.museum().id()))
                                          .build()
                            )
                            .build()
            );
            return PaintingJson.fromPainting(paintingResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

    @NotNull
    @Override
    public PaintingJson addPainting(@NotNull PaintingJson paintingJson) {
        ArtistJson artistJson = paintingJson.artist();
        MuseumJson museumJson = paintingJson.museum();

        try {
            Painting paintingResponse = rococoPaintingServiceBlockingStub.addPainting(
                    Painting.newBuilder()
                            .setId(String.valueOf(paintingJson.id()))
                            .setArtist(
                                    Artist.newBuilder()
                                          .setId(String.valueOf(artistJson.id()))
                                          .setName(artistJson.name())
                                          .setBiography(artistJson.biography())
                                          .setPhoto(artistJson.photo())
                                          .build()
                            )
                            .setContent(paintingJson.content())
                            .setTitle(paintingJson.title())
                            .setDescription(paintingJson.description())
                            .setMuseum(
                                    Museum.newBuilder()
                                          .setId(String.valueOf(museumJson.id()))
                                          .setTitle(museumJson.title())
                                          .setDescription(museumJson.description())
                                          .setPhoto(museumJson.photo())
                                          .setGeo(
                                                  Geo.newBuilder()
                                                     .setCountry(
                                                             Country.newBuilder()
                                                                    .setName(museumJson.geo().country().name())
                                                                    .build()
                                                     )
                                                     .setCity(museumJson.geo().city())
                                                     .build()
                                          )
                                          .build()
                            )
                            .build()
            );
            return PaintingJson.fromPainting(paintingResponse);

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new NotFoundException("The gRPC operation was cancelled", e);
        }
    }

}
