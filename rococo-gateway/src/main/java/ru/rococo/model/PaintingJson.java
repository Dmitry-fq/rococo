package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;
import ru.rococo.config.RococoGatewayServiceConfig;
import ru.rococo.grpc.Painting;

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
    public static @Nonnull PaintingJson fromPainting(@Nonnull Painting painting) {
        return new PaintingJson(
                UUID.fromString(painting.getId()),
                ArtistJson.fromArtist(painting.getArtist()),
                painting.getContent(),
                painting.getTitle(),
                painting.getDescription(),
                MuseumJson.fromMuseum(painting.getMuseum())
        );
    }
}
