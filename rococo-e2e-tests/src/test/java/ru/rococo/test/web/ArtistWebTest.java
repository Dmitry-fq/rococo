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
        String imagePath = "img/artists/botticelli.jpg";

        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickArtistButton()
                .checkElements()
                .clickAddArtistButton()
                .checkElements()
                .setArtistNameInput(artistName)
                .setArtistPhotoInput(imagePath)
                .setBiographyInput(DataUtils.randomText())
                .clickAddButton()
                .checkToastArtistAdded(artistName)
                .findArtist(artistName)
                .checkArtistNameAndPhoto(artistName, imagePath)
                .findArtistOnPageAndClick(artistName);
    }
}
