package ru.rococo.service;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.Nonnull;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.rococo.ex.ArtistNotFoundException;
import ru.rococo.grpc.Artist;
import ru.rococo.grpc.RococoArtistServiceGrpc;

@Component
public class ArtistClient {

    private static final Logger LOG = LoggerFactory.getLogger(ArtistClient.class);

    @GrpcClient("artistClient")
    private RococoArtistServiceGrpc.RococoArtistServiceBlockingStub rococoArtistServiceStub;

    public @Nonnull Artist getArtist(Artist artistRequest) {
        try {
            return rococoArtistServiceStub.getArtist(
                    Artist.newBuilder()
                          .setId(artistRequest.getId())
                          .build()
            );

        } catch (StatusRuntimeException e) {
            LOG.error("### Error while calling gRPC server ", e);

            if (e.getStatus().getCode() == Status.NOT_FOUND.getCode()) {
                throw new ArtistNotFoundException(e.getMessage());
            } else {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The gRPC operation was cancelled", e);
            }
        }
    }
}
