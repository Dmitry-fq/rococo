package ru.rococo.test.api.artist;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.model.ArtistJson;
import ru.rococo.service.impl.ArtistGrpcClient;
import ru.rococo.utils.DataUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.rococo.utils.Constants.ARTIST_ID_NOT_FOUND;
import static ru.rococo.utils.Constants.INVALID_UUID;

@ApiTest
public class UpdateArtistTest {

    private final ArtistGrpcClient artistGrpcClient = new ArtistGrpcClient();

    @Artist
    @Test
    void updateArtistShouldBeSuccess(ArtistJson artist) {
        ArtistJson newArtist = new ArtistJson(
                artist.id(),
                DataUtils.randomArtistName(),
                DataUtils.randomText(),
                DataUtils.getImageByPathOrEmpty("img/artists/botticelli.jpg")
        );

        ArtistJson artistJson = artistGrpcClient.updateArtist(newArtist);

        assertThat(artistJson).isEqualTo(newArtist);

    }

    @Test
    void artistNotExistShouldBeFail() {
        UUID incorrectUuid = UUID.randomUUID();
        ArtistJson newArtist = new ArtistJson(
                incorrectUuid,
                DataUtils.randomArtistName(),
                DataUtils.randomText(),
                DataUtils.getImageByPathOrEmpty("img/artists/botticelli.jpg")
        );

        assertThatThrownBy(() -> artistGrpcClient.updateArtist(newArtist))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(ARTIST_ID_NOT_FOUND, incorrectUuid));
    }

    @Test
    void incorrectFormatUuidShouldBeFail() {
        ArtistJson newArtist = new ArtistJson(
                null,
                DataUtils.randomArtistName(),
                DataUtils.randomText(),
                DataUtils.getImageByPathOrEmpty("img/artists/botticelli.jpg")
        );

        assertThatThrownBy(() -> artistGrpcClient.updateArtist(newArtist))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(INVALID_UUID + "null");
    }
}
