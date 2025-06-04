package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import ru.rococo.config.RococoGatewayServiceConfig;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaintingJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("artist")
        ArtistJson artist,

        @JsonProperty("content")
        @Size(max = RococoGatewayServiceConfig.ONE_MB)
        String content,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("museum")
        MuseumJson museum
) {
}
