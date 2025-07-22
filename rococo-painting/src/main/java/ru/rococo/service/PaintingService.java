package ru.rococo.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.rococo.data.PaintingEntity;
import ru.rococo.data.repository.PaintingRepository;
import ru.rococo.ex.PaintingNotFoundException;
import ru.rococo.grpc.AllPaintingsByAuthorIdRequest;
import ru.rococo.grpc.AllPaintingsRequest;
import ru.rococo.grpc.AllPaintingsResponse;
import ru.rococo.grpc.Artist;
import ru.rococo.grpc.Museum;
import ru.rococo.grpc.Painting;
import ru.rococo.grpc.RococoPaintingServiceGrpc;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@GrpcService
public class PaintingService extends RococoPaintingServiceGrpc.RococoPaintingServiceImplBase {

    private final PaintingRepository paintingRepository;

    private final ArtistClient artistClient;

    private final MuseumClient museumClient;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository, ArtistClient artistClient, MuseumClient museumClient) {
        this.paintingRepository = paintingRepository;
        this.artistClient = artistClient;
        this.museumClient = museumClient;
    }

    @Override
    public void getPainting(Painting paintingRequest, StreamObserver<Painting> responseObserver) {
        String paintingId = paintingRequest.getId();
        Painting paintingResponse = paintingRepository.findById(UUID.fromString(paintingId))
                                                      .map(paintingEntity -> paintingEntity.toPainting(
                                                                      getArtistById(String.valueOf(paintingEntity.getArtistId())),
                                                                      getMuseumById(String.valueOf(paintingEntity.getMuseumId()))
                                                              )
                                                      )
                                                      .orElseThrow(() -> new PaintingNotFoundException(
                                                              "Painting with id: `" + paintingId + "` not found")
                                                      );

        responseObserver.onNext(paintingResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void allPaintings(AllPaintingsRequest request, StreamObserver<AllPaintingsResponse> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPageable().getPage(), request.getPageable().getSize());
        AllPaintingsResponse response = AllPaintingsResponse.newBuilder().addAllPaintings(
                paintingRepository.findAllByTitleContainingIgnoreCase(request.getTitle(), pageable).stream()
                                  .map(paintingEntity -> paintingEntity.toPainting(
                                                  getArtistById(String.valueOf(paintingEntity.getArtistId())),
                                                  getMuseumById(String.valueOf(paintingEntity.getMuseumId()))
                                          )
                                  )
                                  .toList()
        ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void allPaintingsByAuthorId(AllPaintingsByAuthorIdRequest request, StreamObserver<AllPaintingsResponse> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPageable().getPage(), request.getPageable().getSize());
        AllPaintingsResponse response = AllPaintingsResponse.newBuilder().addAllPaintings(
                paintingRepository.findAllByArtistId(UUID.fromString(request.getAuthorId()), pageable).stream()
                                  .map(paintingEntity -> paintingEntity.toPainting(
                                                  getArtistById(String.valueOf(paintingEntity.getArtistId())),
                                                  getMuseumById(String.valueOf(paintingEntity.getMuseumId()))
                                          )
                                  )
                                  .toList()
        ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updatePainting(Painting paintingRequest, StreamObserver<Painting> responseObserver) {
        String paintingId = paintingRequest.getId();
        PaintingEntity paintingEntity = paintingRepository.findById(UUID.fromString(paintingId))
                                                          .orElseThrow(() -> new PaintingNotFoundException(
                                                                  "Painting with id: `" + paintingId + "` not found")
                                                          );

        updatePaintingEntityFromRequest(paintingEntity, paintingRequest);
        Painting painting = paintingEntity.toPainting(
                getArtistById(String.valueOf(paintingEntity.getArtistId())),
                getMuseumById(String.valueOf(paintingEntity.getMuseumId()))
        );
        paintingRepository.save(paintingEntity);

        responseObserver.onNext(painting);
        responseObserver.onCompleted();
    }

    @Override
    public void addPainting(Painting paintingRequest, StreamObserver<Painting> responseObserver) {
        PaintingEntity paintingEntity = new PaintingEntity();
        updatePaintingEntityFromRequest(paintingEntity, paintingRequest);

        Artist artist = getArtistById(String.valueOf(paintingEntity.getArtistId()));
        Museum museum = getMuseumById(String.valueOf(paintingEntity.getMuseumId()));

        paintingRepository.save(paintingEntity);

        Painting painting = paintingEntity.toPainting(artist, museum);
        responseObserver.onNext(painting);
        responseObserver.onCompleted();
    }

    private Artist getArtistById(String id) {
        return artistClient.getArtist(
                Artist.newBuilder()
                      .setId(id)
                      .build()
        );
    }

    private Museum getMuseumById(String id) {
        return museumClient.getMuseum(
                Museum.newBuilder()
                      .setId(id)
                      .build()
        );
    }

    private void updatePaintingEntityFromRequest(PaintingEntity paintingEntity, Painting paintingRequest) {
        paintingEntity.setArtistId(UUID.fromString(paintingRequest.getArtist().getId()));
        String content = paintingRequest.getContent();
        if (isContentString(content)) {
            paintingEntity.setContent(content.getBytes(StandardCharsets.UTF_8));
        }
        paintingEntity.setTitle(paintingRequest.getTitle());
        paintingEntity.setDescription(paintingRequest.getDescription());
        paintingEntity.setMuseumId(UUID.fromString(paintingRequest.getMuseum().getId()));
    }

    private boolean isContentString(String content) {
        return content != null && content.startsWith("data:image");
    }

}
