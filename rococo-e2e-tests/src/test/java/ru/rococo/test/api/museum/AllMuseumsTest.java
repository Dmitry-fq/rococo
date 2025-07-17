package ru.rococo.test.api.museum;

import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.model.MuseumJson;
import ru.rococo.service.impl.MuseumGrpcClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ApiTest
public class AllMuseumsTest {

    private final MuseumGrpcClient museumGrpcClient = new MuseumGrpcClient();

    @Museum
    @Test
    void museumExistShouldBeSuccess(MuseumJson museum) {
        List<MuseumJson> museums = museumGrpcClient.allMuseums(museum.title(), 0, 10);

        assertThat(museums).hasSize(1);
        assertThat(museums.getFirst()).isEqualTo(museum);
    }

    @Museum
    @Test
    void allMuseumsWithoutTitleShouldBeSuccess() {
        int size = 10;
        List<MuseumJson> museums = museumGrpcClient.allMuseums("", 0, size);

        assertThat(museums).hasSizeBetween(1, size);
    }

    @Museum
    @Test
    void allMuseumsWithPartTitleShouldBeSuccess(MuseumJson museum) {
        int size = 10;
        String partTitle = museum.title().substring(0, 3);
        List<MuseumJson> museums = museumGrpcClient.allMuseums(partTitle, 0, size);

        assertThat(museums).hasSizeBetween(1, size);
    }
}
