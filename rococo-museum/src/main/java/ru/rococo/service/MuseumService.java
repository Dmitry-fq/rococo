package ru.rococo.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rococo.data.MuseumEntity;
import ru.rococo.data.repository.MuseumRepository;
import ru.rococo.grpc.AllMuseumsRequest;
import ru.rococo.grpc.AllMuseumsResponse;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.Museum;
import ru.rococo.grpc.RococoMuseumServiceGrpc;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@GrpcService
public class MuseumService extends RococoMuseumServiceGrpc.RococoMuseumServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumService.class);

    private final MuseumRepository museumRepository;

    private final GeoClient geoClient;

    @Autowired
    public MuseumService(MuseumRepository museumRepository, GeoClient geoClient) {
        this.museumRepository = museumRepository;
        this.geoClient = geoClient;
    }

    @Override
    public void getMuseum(Museum museumRequest, StreamObserver<Museum> responseObserver) {
        String museumId = museumRequest.getId();
        Museum museumResponse = museumRepository.findById(UUID.fromString(museumId))
                                                .map(MuseumEntity::toMuseum)
                                                .orElseThrow(() -> new RuntimeException(
                                                        "Museum with id: `" + museumId + "` not found")
                                                );

        responseObserver.onNext(museumResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void allMuseums(AllMuseumsRequest request, StreamObserver<AllMuseumsResponse> responseObserver) {
        AllMuseumsResponse response = AllMuseumsResponse.newBuilder().addAllMuseums(
                museumRepository.findAll().stream()
                                .map(MuseumEntity::toMuseum)
                                .toList()
        ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateMuseum(Museum museumRequest, StreamObserver<Museum> responseObserver) {
        String museumId = museumRequest.getId();
        MuseumEntity museumEntity = museumRepository.findById(UUID.fromString(museumId))
                                                    .orElseThrow(() -> new RuntimeException(
                                                            "Museum with id: `" + museumId + "` not found")
                                                    );
        String country = getCountryNameById(museumRequest.getGeo().getCountry().getId());
        updateMuseumEntityFromRequest(museumEntity, museumRequest, country);

        museumRepository.save(museumEntity);

        responseObserver.onNext(museumEntity.toMuseum());
        responseObserver.onCompleted();
    }

    @Override
    public void addMuseum(Museum museumRequest, StreamObserver<Museum> responseObserver) {
        MuseumEntity museumEntity = new MuseumEntity();
        String country = getCountryNameById(museumRequest.getGeo().getCountry().getId());
        updateMuseumEntityFromRequest(museumEntity, museumRequest, country);

        museumRepository.save(museumEntity);

        responseObserver.onNext(museumEntity.toMuseum());
        responseObserver.onCompleted();
    }

    private String getCountryNameById(String countryId) {
        Country countryResponse = geoClient.getCountry(
                Country.newBuilder()
                       .setId(countryId)
                       .build());

        return countryResponse.getName();
    }

    private void updateMuseumEntityFromRequest(MuseumEntity museumEntity, Museum museumRequest, String country) {
        museumEntity.setTitle(museumRequest.getTitle());
        museumEntity.setDescription(museumRequest.getDescription());
        String photo = museumRequest.getPhoto();
        if (isPhotoString(photo)) {
            museumEntity.setPhoto(photo.getBytes(StandardCharsets.UTF_8));
        }
        museumEntity.setCountryId(
                UUID.fromString(
                        museumRequest.getGeo().getCountry().getId()
                )
        );
        museumEntity.setCountry(country);
        museumEntity.setCity(museumRequest.getGeo().getCity());
    }

    private boolean isPhotoString(String photo) {
        return photo != null && photo.startsWith("data:image");
    }

}
