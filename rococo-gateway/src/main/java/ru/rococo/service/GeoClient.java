package ru.rococo.service;

import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.rococo.grpc.AllCountriesResponse;
import ru.rococo.grpc.RococoGeoServiceGrpc;
import ru.rococo.model.CountryJson;

import java.util.List;

@Component
public class GeoClient {

    private static final Logger LOG = LoggerFactory.getLogger(GeoClient.class);

    @GrpcClient("geoClient")
    private RococoGeoServiceGrpc.RococoGeoServiceBlockingStub rococoGeoServiceStub;

    public Page<CountryJson> allCountries(@Nonnull Pageable pageable) {
        try {
            AllCountriesResponse allCountriesResponse = rococoGeoServiceStub.allCountries(
                    ru.rococo.grpc.Pageable.newBuilder()
                                           .setPage(pageable.getPageNumber())
                                           .setSize(pageable.getPageSize())
                                           .build()

            );

            List<CountryJson> countryList = allCountriesResponse.getCountriesList().stream()
                                                                .map(CountryJson::fromCountryResponse)
                                                                .toList();

            return new PageImpl<>(countryList, pageable, allCountriesResponse.getTotalCount());

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }
}
