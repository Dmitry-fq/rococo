package ru.rococo.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.rococo.data.CountryEntity;
import ru.rococo.data.repository.CountryRepository;
import ru.rococo.grpc.AllCountriesResponse;
import ru.rococo.grpc.Pageable;
import ru.rococo.grpc.RococoGeoServiceGrpc;

@GrpcService
public class GeoService extends RococoGeoServiceGrpc.RococoGeoServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(GeoService.class);

    private final CountryRepository countryRepository;

    @Autowired
    public GeoService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void allCountries(Pageable pageable, StreamObserver<AllCountriesResponse> responseObserver) {
        AllCountriesResponse response = AllCountriesResponse.newBuilder().addAllCountries(
                countryRepository.findAll().stream()
                                 .map(CountryEntity::toCountry)
                                 .toList()
        ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
