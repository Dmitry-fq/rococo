package ru.rococo.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.rococo.data.CountryEntity;
import ru.rococo.data.repository.CountryRepository;
import ru.rococo.ex.CountryNotFoundException;
import ru.rococo.grpc.AllCountriesResponse;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.Pageable;
import ru.rococo.grpc.RococoGeoServiceGrpc;

import java.util.UUID;

@GrpcService
public class GeoService extends RococoGeoServiceGrpc.RococoGeoServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(GeoService.class);

    private final CountryRepository countryRepository;

    @Autowired
    public GeoService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public void getCountry(Country countryRequest, StreamObserver<Country> responseObserver) {
        String countryId = countryRequest.getId();
        Country response = countryRepository.findById(UUID.fromString(countryId))
                                            .map(CountryEntity::toCountry)
                                            .orElseThrow(() -> new CountryNotFoundException(
                                                    "Country with id: `" + countryId + "` not found")
                                            );

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCountryByName(Country countryRequest, StreamObserver<Country> responseObserver) {
        Country response = countryRepository.findByName(countryRequest.getName())
                                            .map(CountryEntity::toCountry)
                                            .orElseThrow(() -> new CountryNotFoundException(
                                                    "Country with name: `" + countryRequest.getName() + "` not found")
                                            );

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void allCountries(Pageable pageable, StreamObserver<AllCountriesResponse> responseObserver) {
        org.springframework.data.domain.Pageable pageableGrpc = PageRequest.of(pageable.getPage(), pageable.getSize());
        AllCountriesResponse response = AllCountriesResponse.newBuilder().addAllCountries(
                countryRepository.findAll(pageableGrpc).stream()
                                 .map(CountryEntity::toCountry)
                                 .toList()
        ).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
