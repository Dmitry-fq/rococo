package ru.rococo.test.api.artist;

import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.model.ArtistJson;
import ru.rococo.service.impl.ArtistGrpcClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ApiTest
public class AllArtistsTest {

    private final ArtistGrpcClient artistGrpcClient = new ArtistGrpcClient();

    @Artist
    @Test
    void artistExistShouldBeSuccess(ArtistJson artist) {
        int size = 10;
        List<ArtistJson> artists = artistGrpcClient.allArtists(artist.name(), 0, size);

        assertThat(artists).hasSizeBetween(1, size);
        assertThat(artists.getFirst()).isEqualTo(artist);
    }

    @Artist
    @Test
    void allArtistWithoutNameShouldBeSuccess() {
        int size = 10;
        List<ArtistJson> artists = artistGrpcClient.allArtists("", 0, size);

        assertThat(artists).hasSizeBetween(1, size);
    }

    @Artist
    @Test
    void allArtistsWithPartTitleShouldBeSuccess(ArtistJson artist) {
        int size = 10;
        String partTitle = artist.name().substring(0, 3);
        List<ArtistJson> artists = artistGrpcClient.allArtists(partTitle, 0, size);

        assertThat(artists).hasSizeBetween(1, size);
    }
}
