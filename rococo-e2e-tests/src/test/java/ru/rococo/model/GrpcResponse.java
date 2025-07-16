package ru.rococo.model;

public record GrpcResponse<T>(
        T message,

        String error
) {
}
