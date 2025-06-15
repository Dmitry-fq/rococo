package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import ru.rococo.grpc.Country;

import java.util.UUID;

public record CountryJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        String name
) {
    public static @Nonnull CountryJson fromCountryResponse(@Nonnull Country country) {
        return new CountryJson(
                UUID.fromString(country.getId()),
                country.getName()
        );
    }
}
