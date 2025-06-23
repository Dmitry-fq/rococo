package ru.rococo.ex.error;

@SuppressWarnings("visibility")
public class ApiError {

    private final String apiVersion;

    private final Error error;

    public ApiError(String apiVersion, String code, String message) {
        this.apiVersion = apiVersion;
        this.error = new Error(
                code,
                message
        );
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public Error getError() {
        return error;
    }

    private record Error(String code, String message) {
    }
}
