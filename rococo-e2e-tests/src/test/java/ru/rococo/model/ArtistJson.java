package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rococo.grpc.Artist;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;
import java.util.UUID;

import static ru.rococo.utils.DataUtils.getImageByPathOrEmpty;
import static ru.rococo.utils.DataUtils.getNotBlankStringOrRandom;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArtistJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("name")
        String name,

        @JsonProperty("biography")
        String biography,

        @JsonProperty("photo")
        String photo
) {
    public static @Nonnull ArtistJson fromArtist(@Nonnull Artist artist) {
        return new ArtistJson(
                UUID.fromString(artist.getId()),
                artist.getName(),
                artist.getBiography(),
                artist.getPhoto()
        );
    }

    public static @Nonnull ArtistJson fromAnnotation(@Nonnull ru.rococo.jupiter.annotation.Artist artistAnnotation) {
        return new ArtistJson(
                null,
                getNotBlankStringOrRandom(artistAnnotation.name(), DataUtils::randomArtistName),
                getNotBlankStringOrRandom(artistAnnotation.biography(), DataUtils::randomText),
                getImageByPathOrEmpty(artistAnnotation.photoPath())
        );
    }
}
