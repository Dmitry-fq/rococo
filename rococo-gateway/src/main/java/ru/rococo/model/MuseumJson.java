package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import ru.rococo.config.RococoGatewayServiceConfig;

import java.util.UUID;

public record MuseumJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("photo")
        @Size(max = RococoGatewayServiceConfig.ONE_MB)
        String photo,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("geo")
        GeoJson geo
) {
}
