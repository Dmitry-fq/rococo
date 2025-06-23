package ru.rococo.ex;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@GrpcAdvice
public class MuseumExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MuseumExceptionHandler.class);

    @Value("${api.version}")
    private String apiVersion;

    @GrpcExceptionHandler(MuseumNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StatusRuntimeException handleMuseumNotFoundException(MuseumNotFoundException ex) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("apiVersion", Metadata.ASCII_STRING_MARSHALLER), apiVersion);

        LOG.error(ex.getMessage(), ex);

        return Status.NOT_FOUND
                .withDescription(ex.getMessage())
                .asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StatusRuntimeException handleIllegalArgumentException(IllegalArgumentException ex) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("apiVersion", Metadata.ASCII_STRING_MARSHALLER), apiVersion);

        LOG.error(ex.getMessage(), ex);

        return Status.INVALID_ARGUMENT
                .withDescription(ex.getMessage())
                .asRuntimeException(metadata);
    }
}
