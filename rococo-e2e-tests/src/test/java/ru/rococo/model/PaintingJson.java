package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rococo.grpc.Painting;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;
import java.util.UUID;

import static ru.rococo.utils.DataUtils.getImageByPathOrEmpty;
import static ru.rococo.utils.DataUtils.getNotBlankStringOrRandom;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaintingJson(

        @JsonProperty("id")
        UUID id,

        @JsonProperty("artist")
        ArtistJson artist,

        @JsonProperty("content")
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

    public static @Nonnull PaintingJson fromAnnotation(
            @Nonnull ru.rococo.jupiter.annotation.Painting paintingAnnotation,
            ArtistJson artistJson,
            MuseumJson museumJson
    ) {
        return new PaintingJson(
                null,
                artistJson,
                getImageByPathOrEmpty(paintingAnnotation.imagePath()),
                getNotBlankStringOrRandom(paintingAnnotation.title(), DataUtils::randomPaintingName),
                getNotBlankStringOrRandom(paintingAnnotation.description(), DataUtils::randomText),
                museumJson
        );
    }
}
