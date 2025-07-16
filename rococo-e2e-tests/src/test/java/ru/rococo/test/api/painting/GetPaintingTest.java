package ru.rococo.test.api.painting;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Painting;
import ru.rococo.model.PaintingJson;
import ru.rococo.service.impl.PaintingGrpcClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.rococo.utils.Constants.INVALID_UUID;
import static ru.rococo.utils.Constants.PAINTING_ID_NOT_FOUND;

@ApiTest
public class GetPaintingTest {

    private final PaintingGrpcClient paintingGrpcClient = new PaintingGrpcClient();

    @Painting
    @Test
    void getPaintingShouldBeSuccess(PaintingJson painting) {
        PaintingJson paintingJson = paintingGrpcClient.getPainting(String.valueOf(painting.id()));

        assertThat(paintingJson).isEqualTo(painting);
    }

    @Test
    void paintingNotExistShouldBeFail() {
        String incorrectUuid = String.valueOf(UUID.randomUUID());

        assertThatThrownBy(() -> paintingGrpcClient.getPainting(incorrectUuid))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(PAINTING_ID_NOT_FOUND, incorrectUuid));
    }

    @Test
    void incorrectFormatUuidShouldBeFail() {
        String incorrectFormatUuid = "123";

        assertThatThrownBy(() -> paintingGrpcClient.getPainting(incorrectFormatUuid))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(INVALID_UUID + incorrectFormatUuid);
    }
}
