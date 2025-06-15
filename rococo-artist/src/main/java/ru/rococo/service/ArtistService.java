package ru.rococo.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static boolean isPhotoString(String photo) {
        return photo != null && photo.startsWith("data:image");
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
        ArtistsResponse response = ArtistsResponse.newBuilder().addAllArtistList(
                artistRepository.findAll().stream()
                                .filter(artistEntity -> request.getName().isBlank() ||
                                        artistEntity.getName().contains(request.getName()))
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
        artistEntity.setName(artistRequest.getName());
        artistEntity.setBiography(artistRequest.getBiography());
        String photo = artistRequest.getPhoto();
        if (isPhotoString(photo)) {
            artistEntity.setPhoto(photo.getBytes(StandardCharsets.UTF_8));
        }

        artistRepository.save(artistEntity);

        responseObserver.onNext(artistEntity.toArtist());
        responseObserver.onCompleted();
    }

    @Override
    public void addArtist(Artist artistRequest, StreamObserver<Artist> responseObserver) {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName(artistRequest.getName());
        artistEntity.setBiography(artistRequest.getBiography());
        if (isPhotoString(artistRequest.getPhoto())) {
            artistEntity.setPhoto(artistRequest.getPhoto().getBytes(StandardCharsets.UTF_8));
        }

        artistRepository.save(artistEntity);

        responseObserver.onNext(artistEntity.toArtist());
        responseObserver.onCompleted();
    }
}
