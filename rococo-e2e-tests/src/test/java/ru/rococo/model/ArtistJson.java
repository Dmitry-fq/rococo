package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rococo.grpc.Artist;

import javax.annotation.Nonnull;
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
}
