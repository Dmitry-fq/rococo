package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rococo.grpc.Country;

import javax.annotation.Nonnull;
import java.util.UUID;

public record CountryJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        String name
) {
    public static @Nonnull CountryJson fromCountry(@Nonnull Country country) {
        return new CountryJson(
                UUID.fromString(country.getId()),
                country.getName()
        );
    }
}
