package ru.rococo.test.web;

import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebTest
public class ArtistWebTest {

    @User
    @ApiLogin
    @Artist(
            photo = "img1/artists/botticelli.jpg"
    )
    @Test
    void artistTest() {
        assertEquals(1, 1);
    }
}
