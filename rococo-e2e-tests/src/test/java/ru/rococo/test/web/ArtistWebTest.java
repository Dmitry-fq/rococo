package ru.rococo.test.web;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.jupiter.annotation.Painting;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebTest
public class ArtistWebTest {

    @User
    @ApiLogin
    @Painting(
            artist = @Artist(
                    name = "artistPainting2",
                    biography = "artistPainting2",
                    photoPath = "img/artists/botticelli.jpg"
            ),
            museum = @Museum(
                    title = "museumPainting2",
                    description = "museumPainting2",
                    photoPath = "img/museums/louvre.jpg",
                    country = "Russia",
                    city = "Moscow"
            ),
            imagePath = "img/paintings/mona_lisa.jpg",
            title = "paintingAnnotation",
            description = "paintingAnnotation"
    )
    @Disabled
    @Test
    void artistTest() {
        assertEquals(1, 1);
    }
}
