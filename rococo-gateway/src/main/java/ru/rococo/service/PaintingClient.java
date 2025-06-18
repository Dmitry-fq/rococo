package ru.rococo.service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.rococo.grpc.AllPaintingsByAuthorIdRequest;
import ru.rococo.grpc.AllPaintingsRequest;
import ru.rococo.grpc.AllPaintingsResponse;
import ru.rococo.grpc.Artist;
import ru.rococo.grpc.Museum;
import ru.rococo.grpc.Painting;
import ru.rococo.grpc.RococoPaintingServiceGrpc;
import ru.rococo.model.PaintingJson;

import javax.annotation.Nonnull;
import java.util.List;

@Component
public class PaintingClient {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumClient.class);

    @GrpcClient("paintingClient")
    private RococoPaintingServiceGrpc.RococoPaintingServiceBlockingStub rococoPaintingServiceStub;

    public @Nonnull PaintingJson getPainting(@Nonnull String id) {
        try {
            Painting paintingResponse = rococoPaintingServiceStub.getPainting(
                    Painting.newBuilder()
                            .setId(id)
                            .build()
            );
            return PaintingJson.fromPainting(paintingResponse);

        } catch (StatusRuntimeException e) {

            //TODO в константу
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull Page<PaintingJson> allPaintings(String title, @Nonnull Pageable pageable) {
        try {
            AllPaintingsResponse paintingsResponse = rococoPaintingServiceStub.allPaintings(
                    AllPaintingsRequest.newBuilder()
                                       .setTitle(title)
                                       .setPageable(
                                               ru.rococo.grpc.Pageable.newBuilder()
                                                                      .setPage(pageable.getPageNumber())
                                                                      .setSize(pageable.getPageSize())
                                                                      .build()
                                       )
                                       .build()

            );

            List<PaintingJson> paintingList = paintingsResponse.getPaintingsList().stream()
                                                               .map(PaintingJson::fromPainting)
                                                               .toList();

            return new PageImpl<>(paintingList, pageable, paintingsResponse.getTotalCount());

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull Page<PaintingJson> getPaintingsByAuthorId(String authorId, @Nonnull Pageable pageable) {
        try {
            AllPaintingsResponse paintingsResponse = rococoPaintingServiceStub.allPaintingsByAuthorId(
                    AllPaintingsByAuthorIdRequest.newBuilder()
                                                 .setAuthorId(authorId)
                                                 .setPageable(
                                                         ru.rococo.grpc.Pageable.newBuilder()
                                                                                .setPage(pageable.getPageNumber())
                                                                                .setSize(pageable.getPageSize())
                                                                                .build()
                                                 )
                                                 .build()

            );

            List<PaintingJson> paintingList = paintingsResponse.getPaintingsList().stream()
                                                               .map(PaintingJson::fromPainting)
                                                               .toList();

            return new PageImpl<>(paintingList, pageable, paintingsResponse.getTotalCount());

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull PaintingJson addPainting(@Nonnull PaintingJson paintingJson) {
        try {
            Painting paintingResponse = rococoPaintingServiceStub.addPainting(
                    Painting.newBuilder()
                            .setId(String.valueOf(paintingJson.id()))
                            .setArtist(buildArtist(paintingJson))
                            .setContent(paintingJson.content())
                            .setTitle(paintingJson.title())
                            .setDescription(paintingJson.description())
                            .setMuseum(buildMuseum(paintingJson))
                            .build()
            );
            return PaintingJson.fromPainting(paintingResponse);

        } catch (StatusRuntimeException e) {

            //TODO в константу
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    public @Nonnull PaintingJson updatePainting(@Nonnull PaintingJson paintingJson) {
        try {
            Painting paintingResponse = rococoPaintingServiceStub.updatePainting(
                    Painting.newBuilder()
                            .setId(String.valueOf(paintingJson.id()))
                            .setArtist(buildArtist(paintingJson))
                            .setContent(paintingJson.content())
                            .setTitle(paintingJson.title())
                            .setDescription(paintingJson.description())
                            .setMuseum(buildMuseum(paintingJson))
                            .build()
            );
            return PaintingJson.fromPainting(paintingResponse);

        } catch (StatusRuntimeException e) {

            //TODO в константу
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }

    private Artist buildArtist(PaintingJson paintingJson) {
        return Artist.newBuilder()
                     .setId(String.valueOf(paintingJson.artist().id()))
                     .build();
    }

    private Museum buildMuseum(PaintingJson paintingJson) {
        return Museum.newBuilder()
                     .setId(String.valueOf(paintingJson.museum().id()))
                     .build();
    }
}
