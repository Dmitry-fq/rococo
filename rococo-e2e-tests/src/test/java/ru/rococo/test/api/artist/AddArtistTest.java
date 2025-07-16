package ru.rococo.test.api.artist;

import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.model.ArtistJson;
import ru.rococo.service.impl.ArtistGrpcClient;
import ru.rococo.utils.DataUtils;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ApiTest
public class AddArtistTest {

    private final ArtistGrpcClient artistGrpcClient = new ArtistGrpcClient();

    @Test
    void addArtistShouldBeSuccess() {
        ArtistJson newArtist = new ArtistJson(
                null,
                DataUtils.randomArtistName(),
                DataUtils.randomText(),
                DataUtils.getImageByPathOrEmpty("img/artists/botticelli.jpg")
        );

        ArtistJson addedArtistJson = artistGrpcClient.addArtist(newArtist);
        ArtistJson actualArtistJson = artistGrpcClient.getArtist(String.valueOf(addedArtistJson.id()));

        assertSoftly(softly -> {
            softly.assertThat(actualArtistJson.name()).isEqualTo(newArtist.name());
            softly.assertThat(actualArtistJson.biography()).isEqualTo(newArtist.biography());
            softly.assertThat(actualArtistJson.photo()).isEqualTo(newArtist.photo());
        });
    }
}
