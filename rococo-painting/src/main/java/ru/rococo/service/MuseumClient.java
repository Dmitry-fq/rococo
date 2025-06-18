package ru.rococo.service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.rococo.grpc.Museum;
import ru.rococo.grpc.RococoMuseumServiceGrpc;

@Component
public class MuseumClient {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumClient.class);

    @GrpcClient("museumClient")
    private RococoMuseumServiceGrpc.RococoMuseumServiceBlockingStub rococoMuseumServiceStub;

    public Museum getMuseum(Museum museumRequest) {
        try {
            return rococoMuseumServiceStub.getMuseum(
                    Museum.newBuilder()
                          .setId(museumRequest.getId())
                          .build()
            );

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
        }
    }
}
