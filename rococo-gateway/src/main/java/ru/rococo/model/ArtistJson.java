package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import ru.rococo.config.RococoGatewayServiceConfig;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArtistJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        String name,

        @JsonProperty("biography")
        String biography,

        @JsonProperty("photo")
        @Size(max = RococoGatewayServiceConfig.ONE_MB)
        String photo
) {
}
