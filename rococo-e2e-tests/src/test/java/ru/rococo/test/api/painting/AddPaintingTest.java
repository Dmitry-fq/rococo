package ru.rococo.test.api.painting;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.model.ArtistJson;
import ru.rococo.model.CountryJson;
import ru.rococo.model.GeoJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.model.PaintingJson;
import ru.rococo.service.impl.GeoGrpcClient;
import ru.rococo.service.impl.PaintingGrpcClient;
import ru.rococo.utils.DataUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static ru.rococo.utils.Constants.ARTIST_ID_NOT_FOUND;
import static ru.rococo.utils.Constants.MUSEUM_ID_NOT_FOUND;

@ApiTest
public class AddPaintingTest {

    private final PaintingGrpcClient paintingGrpcClient = new PaintingGrpcClient();

    private final GeoGrpcClient geoGrpcClient = new GeoGrpcClient();

    @Artist
    @Museum
    @Test
    void updatePaintingShouldBeSuccess(ArtistJson artist, MuseumJson museum) {
        PaintingJson newPainting = new PaintingJson(
                null,
                artist,
                DataUtils.getImageByPathOrEmpty("img/paintings/birth_of_venus.jpg"),
                DataUtils.randomPaintingName(),
                DataUtils.randomText(),
                museum
        );

        PaintingJson addedPainting = paintingGrpcClient.addPainting(newPainting);
        PaintingJson actualPainting = paintingGrpcClient.getPainting(String.valueOf(addedPainting.id()));

        assertSoftly(softly -> {
            softly.assertThat(actualPainting.artist()).isEqualTo(newPainting.artist());
            softly.assertThat(actualPainting.content()).isEqualTo(newPainting.content());
            softly.assertThat(actualPainting.title()).isEqualTo(newPainting.title());
            softly.assertThat(actualPainting.description()).isEqualTo(newPainting.description());
            softly.assertThat(actualPainting.museum()).isEqualTo(newPainting.museum());
        });
    }

    @Artist
    @Test
    void museumNotExistShouldBeFail(ArtistJson artist) {
        UUID incorrectMuseumUuid = UUID.randomUUID();
        CountryJson country = geoGrpcClient.allCountries(0, 1).getFirst();
        PaintingJson newPainting = new PaintingJson(
                null,
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

        assertThatThrownBy(() -> paintingGrpcClient.addPainting(newPainting))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(MUSEUM_ID_NOT_FOUND, incorrectMuseumUuid));
    }

    @Museum
    @Test
    void artistNotExistShouldBeFail(PaintingJson painting, MuseumJson museum) {
        UUID incorrectArtistUuid = UUID.randomUUID();
        PaintingJson newPainting = new PaintingJson(
                null,
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

        assertThatThrownBy(() -> paintingGrpcClient.addPainting(newPainting))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(ARTIST_ID_NOT_FOUND, incorrectArtistUuid));
    }
}
