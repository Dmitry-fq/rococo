package ru.rococo.test.api.painting;

import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Painting;
import ru.rococo.model.PaintingJson;
import ru.rococo.service.impl.PaintingGrpcClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ApiTest
public class AllPaintingsTest {

    private final PaintingGrpcClient paintingGrpcClient = new PaintingGrpcClient();

    @Painting
    @Test
    void allPaintingsShouldBeSuccess(PaintingJson painting) {
        List<PaintingJson> paintings = paintingGrpcClient.allPaintings(painting.title(), 0, 10);

        assertThat(paintings).hasSize(1);
        assertThat(paintings.getFirst()).isEqualTo(painting);
    }

    @Painting
    @Test
    void allPaintingsWithoutTitleShouldBeSuccess() {
        int size = 1;
        List<PaintingJson> paintings = paintingGrpcClient.allPaintings("", 0, size);

        assertThat(paintings).hasSize(size);
    }

    @Painting
    @Test
    void allPaintingsWithPartTitleShouldBeSuccess(PaintingJson painting) {
        String partTitle = painting.title().substring(0, 3);
        int size = 10;
        List<PaintingJson> paintings = paintingGrpcClient.allPaintings(partTitle, 0, size);

        assertThat(paintings).hasSizeBetween(1, size);
    }
}
