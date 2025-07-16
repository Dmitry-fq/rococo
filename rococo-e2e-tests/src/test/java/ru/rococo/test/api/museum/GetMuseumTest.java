package ru.rococo.test.api.museum;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.model.MuseumJson;
import ru.rococo.service.impl.MuseumGrpcClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.rococo.utils.Constants.INVALID_UUID;
import static ru.rococo.utils.Constants.MUSEUM_ID_NOT_FOUND;

@ApiTest
public class GetMuseumTest {

    private final MuseumGrpcClient museumGrpcClient = new MuseumGrpcClient();

    @Museum
    @Test
    void getMuseumShouldBeSuccess(MuseumJson museum) {
        MuseumJson museumJson = museumGrpcClient.getMuseum(String.valueOf(museum.id()));

        assertThat(museumJson).isEqualTo(museum);
    }

    @Test
    void museumNotExistShouldBeFail() {
        String incorrectUuid = String.valueOf(UUID.randomUUID());

        assertThatThrownBy(() -> museumGrpcClient.getMuseum(incorrectUuid))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(MUSEUM_ID_NOT_FOUND, incorrectUuid));
    }

    @Test
    void incorrectFormatUuidShouldBeFail() {
        String incorrectFormatUuid = "123";

        assertThatThrownBy(() -> museumGrpcClient.getMuseum(incorrectFormatUuid))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(INVALID_UUID + incorrectFormatUuid);
    }
}
