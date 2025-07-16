package ru.rococo.test.api.artist;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.model.ArtistJson;
import ru.rococo.service.impl.ArtistGrpcClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.rococo.utils.Constants.ARTIST_ID_NOT_FOUND;
import static ru.rococo.utils.Constants.INVALID_UUID;

@ApiTest
public class GetArtistTest {

    private final ArtistGrpcClient artistGrpcClient = new ArtistGrpcClient();

    @Artist
    @Test
    void artistExistShouldBeSuccess(ArtistJson artist) {
        ArtistJson artistJson = artistGrpcClient.getArtist(String.valueOf(artist.id()));

        assertThat(artistJson).isEqualTo(artist);
    }

    @Test
    void artistNotExistShouldBeFail() {
        String incorrectUuid = String.valueOf(UUID.randomUUID());

        assertThatThrownBy(() -> artistGrpcClient.getArtist(incorrectUuid))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(ARTIST_ID_NOT_FOUND, incorrectUuid));
    }

    @Test
    void incorrectFormatUuidShouldBeFail() {
        String incorrectFormatUuid = "123";

        assertThatThrownBy(() -> artistGrpcClient.getArtist(incorrectFormatUuid))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(INVALID_UUID + incorrectFormatUuid);
    }
}
