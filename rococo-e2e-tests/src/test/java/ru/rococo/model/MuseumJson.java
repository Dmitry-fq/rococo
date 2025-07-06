package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rococo.grpc.Museum;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;
import java.util.UUID;

import static ru.rococo.utils.DataUtils.getImageByPathOrEmpty;
import static ru.rococo.utils.DataUtils.getNotBlankStringOrRandom;

public record MuseumJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("photo")
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

    public static @Nonnull MuseumJson fromAnnotation(@Nonnull ru.rococo.jupiter.annotation.Museum museumAnnotation) {
        return new MuseumJson(
                null,
                getNotBlankStringOrRandom(museumAnnotation.title(), DataUtils::randomMuseumName),
                getNotBlankStringOrRandom(museumAnnotation.description(), DataUtils::randomText),
                getImageByPathOrEmpty(museumAnnotation.photoPath()),
                new GeoJson(
                        new CountryJson(
                                null,
                                getNotBlankStringOrRandom(
                                        museumAnnotation.country(),
                                        DataUtils::randomCountryName
                                )
                        ),
                        getNotBlankStringOrRandom(museumAnnotation.city(), DataUtils::randomCityName)
                )
        );
    }
}
