package ru.rococo.test.api.painting;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.jupiter.annotation.Painting;
import ru.rococo.model.ArtistJson;
import ru.rococo.model.CountryJson;
import ru.rococo.model.GeoJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.model.PaintingJson;
import ru.rococo.service.impl.GeoGrpcClient;
import ru.rococo.service.impl.PaintingGrpcClient;
import ru.rococo.utils.DataUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.rococo.utils.Constants.ARTIST_ID_NOT_FOUND;
import static ru.rococo.utils.Constants.INVALID_UUID;
import static ru.rococo.utils.Constants.MUSEUM_ID_NOT_FOUND;
import static ru.rococo.utils.Constants.PAINTING_ID_NOT_FOUND;

@ApiTest
public class UpdatePaintingTest {

    private final PaintingGrpcClient paintingGrpcClient = new PaintingGrpcClient();

    private final GeoGrpcClient geoGrpcClient = new GeoGrpcClient();

    @Painting(imagePath = "img/paintings/mona_lisa.jpg")
    @Artist
    @Museum
    @Test
    void updatePaintingShouldBeSuccess(PaintingJson painting, ArtistJson artist, MuseumJson museum) {
        PaintingJson newPainting = new PaintingJson(
                painting.id(),
                artist,
                DataUtils.getImageByPathOrEmpty("img/paintings/birth_of_venus.jpg"),
                DataUtils.randomPaintingName(),
                DataUtils.randomText(),
                museum
        );

        paintingGrpcClient.updatePainting(newPainting);
        PaintingJson updatedPaintingJson = paintingGrpcClient.getPainting(String.valueOf(painting.id()));

        assertThat(updatedPaintingJson).isEqualTo(newPainting);
    }

    @Painting
    @Artist
    @Museum
    @Test
    void paintingNotExistShouldBeFail(ArtistJson artist, MuseumJson museum) {
        UUID incorrectPaintingUuid = UUID.randomUUID();
        PaintingJson newPainting = new PaintingJson(
                incorrectPaintingUuid,
                artist,
                "",
                DataUtils.randomPaintingName(),
                DataUtils.randomText(),
                museum
        );

        assertThatThrownBy(() -> paintingGrpcClient.updatePainting(newPainting))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(PAINTING_ID_NOT_FOUND, incorrectPaintingUuid));
    }

    @Painting
    @Artist
    @Museum
    @Test
    void incorrectFormatUuidShouldBeFail(ArtistJson artist, MuseumJson museum) {
        PaintingJson newPainting = new PaintingJson(
                null,
                artist,
                "",
                DataUtils.randomPaintingName(),
                DataUtils.randomText(),
                museum
        );

        assertThatThrownBy(() -> paintingGrpcClient.updatePainting(newPainting))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(INVALID_UUID + "null");
    }

    @Painting
    @Artist
    @Test
    void museumNotExistShouldBeFail(PaintingJson painting, ArtistJson artist) {
        UUID incorrectMuseumUuid = UUID.randomUUID();
        CountryJson country = geoGrpcClient.allCountries(0, 1).getFirst();
        PaintingJson newPainting = new PaintingJson(
                painting.id(),
                artist,
                "",
                DataUtils.randomPaintingName(),
                DataUtils.randomText(),
                new MuseumJson(
                        incorrectMuseumUuid,
                        DataUtils.randomMuseumName(),
                        DataUtils.randomText(),
                        "",
                        new GeoJson(
                                new CountryJson(
                                        country.id(),
                                        country.name()
                                ),
                                DataUtils.randomCityName()
                        )
                )
        );

        assertThatThrownBy(() -> paintingGrpcClient.updatePainting(newPainting))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(MUSEUM_ID_NOT_FOUND, incorrectMuseumUuid));
    }

    @Painting
    @Museum
    @Test
    void artistNotExistShouldBeFail(PaintingJson painting, MuseumJson museum) {
        UUID incorrectArtistUuid = UUID.randomUUID();
        PaintingJson newPainting = new PaintingJson(
                painting.id(),
                new ArtistJson(
                        incorrectArtistUuid,
                        DataUtils.randomArtistName(),
                        DataUtils.randomText(),
                        ""
                ),
                "",
                DataUtils.randomPaintingName(),
                DataUtils.randomText(),
                museum
        );

        assertThatThrownBy(() -> paintingGrpcClient.updatePainting(newPainting))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(ARTIST_ID_NOT_FOUND, incorrectArtistUuid));
    }
}
