package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;
import ru.rococo.config.RococoGatewayServiceConfig;
import ru.rococo.grpc.ArtistResponse;

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
    public static @Nonnull ArtistJson fromArtistResponse(@Nonnull ArtistResponse artistResponse) {
        return new ArtistJson(
                UUID.fromString(artistResponse.getId()),
                artistResponse.getName(),
                artistResponse.getBiography(),
                artistResponse.getPhoto()
        );
    }
}
