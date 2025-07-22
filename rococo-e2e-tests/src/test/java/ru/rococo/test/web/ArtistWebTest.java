package ru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;
import ru.rococo.page.MainPage;
import ru.rococo.utils.DataUtils;

@WebTest
public class ArtistWebTest {

    private static final Config CFG = Config.getInstance();

    @User
    @ApiLogin
    @Test
    void addArtistShouldBeSuccess() {
        String artistName = DataUtils.randomArtistName();
        String artistImagePath = "img/artists/botticelli.jpg";
        String biography = DataUtils.randomText();

        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickArtistButton()
                .checkElements()
                .clickAddArtistButton()
                .checkElements()
                .setArtistNameInput(artistName)
                .setArtistPhotoInput(artistImagePath)
                .setBiographyInput(biography)
                .clickAddButton()
                .checkToastArtistAdded(artistName)
                .findArtist(artistName)
                .checkArtistNameAndPhoto(artistName, artistImagePath)
                .findArtistOnPageAndClick(artistName)
                .checkElements()
                .checkArtistName(artistName)
                .checkArtistBiography(biography)
                .checkArtistPhoto(artistImagePath)
                .checkPaintingListIsEmpty();
    }

    @User
    @ApiLogin
    @Test
    void editArtistShouldBeSuccess() {
        //TODO
    }

    @User
    @ApiLogin
    @Test
    void addPaintingForArtistShouldBeSuccess() {
        //TODO
    }
}
