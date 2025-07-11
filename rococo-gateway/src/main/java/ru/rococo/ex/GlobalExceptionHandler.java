package ru.rococo.ex;

import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.rococo.ex.error.ApiError;

import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${api.version}")
    private String apiVersion;

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ApiError> handleStatusRuntimeException(StatusRuntimeException ex) {
        LOG.error("### " + ex.getStatus().getDescription());

        HttpStatus status = switch (ex.getStatus().getCode()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST;
            case UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED;
            case PERMISSION_DENIED -> HttpStatus.FORBIDDEN;
            case ALREADY_EXISTS -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        return createResponseEntity(getApiVersionFromMetadata(ex), status, ex.getStatus().getDescription());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return createResponseEntity(
                apiVersion,
                HttpStatus.BAD_REQUEST,
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()
        );
    }

    private ResponseEntity<ApiError> createResponseEntity(String apiVersion, HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new ApiError(
                        apiVersion,
                        String.valueOf(httpStatus.value()),
                        message
                ),
                httpStatus
        );
    }

    private String getApiVersionFromMetadata(StatusRuntimeException ex) {
        Metadata.Key<String> metadataKey = Metadata.Key.of("apiVersion", Metadata.ASCII_STRING_MARSHALLER);

        return Optional.ofNullable(ex.getTrailers())
                       .map(trailers -> Objects.requireNonNullElse(trailers.get(metadataKey), "metadata not found"))
                       .orElse("metadata not found");
    }
}
