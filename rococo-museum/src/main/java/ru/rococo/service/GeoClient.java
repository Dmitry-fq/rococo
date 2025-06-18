package ru.rococo.service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.RococoGeoServiceGrpc;

@Component
public class GeoClient {

    private static final Logger LOG = LoggerFactory.getLogger(GeoClient.class);

    @GrpcClient("geoClient")
    private RococoGeoServiceGrpc.RococoGeoServiceBlockingStub rococoGeoServiceStub;

    public Country getCountry(Country countryRequest) {
        try {
            return rococoGeoServiceStub.getCountry(
                    Country.newBuilder()
                           .setId(countryRequest.getId())
                           .build()
            );

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }
}
