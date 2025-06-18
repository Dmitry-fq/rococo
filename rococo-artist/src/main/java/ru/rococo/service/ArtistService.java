package ru.rococo.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.rococo.data.ArtistEntity;
import ru.rococo.data.repository.ArtistRepository;
import ru.rococo.grpc.AllArtistsRequest;
import ru.rococo.grpc.Artist;
import ru.rococo.grpc.ArtistsResponse;
import ru.rococo.grpc.RococoArtistServiceGrpc;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@GrpcService
public class ArtistService extends RococoArtistServiceGrpc.RococoArtistServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public void getArtist(Artist artistRequest, StreamObserver<Artist> responseObserver) {
        String artistId = artistRequest.getId();
        Artist response = artistRepository.findById(UUID.fromString(artistId))
                                          .map(ArtistEntity::toArtist)
                                          .orElseThrow(() -> new RuntimeException(
                                                  "Artist with id: `" + artistId + "` not found")
                                          );

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void allArtists(AllArtistsRequest request, StreamObserver<ArtistsResponse> responseObserver) {
        Pageable pageable = PageRequest.of(request.getPageable().getPage(), request.getPageable().getSize());
        ArtistsResponse response = ArtistsResponse.newBuilder().addAllArtistList(
                artistRepository.findAllByNameContainingIgnoreCase(request.getName(), pageable).stream()
                                .map(ArtistEntity::toArtist)
                                .toList()
        ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateArtist(Artist artistRequest, StreamObserver<Artist> responseObserver) {
        String artistId = artistRequest.getId();
        ArtistEntity artistEntity = artistRepository.findById(UUID.fromString(artistId))
                                                    .orElseThrow(() -> new RuntimeException(
                                                            "Artist with id: `" + artistId + "` not found")
                                                    );
        updateArtistEntityFromRequest(artistEntity, artistRequest);

        artistRepository.save(artistEntity);

        responseObserver.onNext(artistEntity.toArtist());
        responseObserver.onCompleted();
    }

    @Override
    public void addArtist(Artist artistRequest, StreamObserver<Artist> responseObserver) {
        ArtistEntity artistEntity = new ArtistEntity();
        updateArtistEntityFromRequest(artistEntity, artistRequest);

        artistRepository.save(artistEntity);

        responseObserver.onNext(artistEntity.toArtist());
        responseObserver.onCompleted();
    }

    private void updateArtistEntityFromRequest(ArtistEntity artistEntity, Artist artistRequest) {
        artistEntity.setName(artistRequest.getName());
        artistEntity.setBiography(artistRequest.getBiography());
        if (isPhotoString(artistRequest.getPhoto())) {
            artistEntity.setPhoto(artistRequest.getPhoto().getBytes(StandardCharsets.UTF_8));
        }
    }

    private boolean isPhotoString(String photo) {
        return photo != null && photo.startsWith("data:image");
    }
}
