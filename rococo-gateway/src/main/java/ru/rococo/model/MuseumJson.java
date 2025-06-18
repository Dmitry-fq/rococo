package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;
import ru.rococo.config.RococoGatewayServiceConfig;
import ru.rococo.grpc.Museum;

import java.util.UUID;

public record MuseumJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("photo")
        @Size(max = RococoGatewayServiceConfig.ONE_MB)
        String photo,

        @JsonProperty("geo")
        GeoJson geo
) {
    public static @Nonnull MuseumJson fromMuseum(@Nonnull Museum museum) {
        return new MuseumJson(
                UUID.fromString(museum.getId()),
                museum.getTitle(),
                museum.getDescription(),
                museum.getPhoto(),
                new GeoJson(
                        CountryJson.fromCountry(museum.getGeo().getCountry()),
                        museum.getGeo().getCity()
                )
        );
    }
}
