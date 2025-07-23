package ru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;
import ru.rococo.model.ArtistJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.page.MainPage;
import ru.rococo.utils.DataUtils;

@WebTest
public class PaintingWebTest {

    private static final Config CFG = Config.getInstance();

    @User
    @ApiLogin
    @Artist
    @Museum
    @Test
    void addPaintingShouldBeSuccess(ArtistJson artistJson, MuseumJson museumJson) {
        String paintingName = DataUtils.randomPaintingName();
        String paintingImagePath = "img/paintings/mona_lisa.jpg";
        String artistName = artistJson.name();
        String description = DataUtils.randomText();
        String museumTitle = museumJson.title();

        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickPaintingButton()
                .checkElements()
                .clickAddPaintingButton()
                .setPaintingNameInput(paintingName)
                .setPaintingPhotoInput(paintingImagePath)
                .setArtist(artistName)
                .setDescriptionInput(description)
                .setMuseum(museumTitle)
                .clickAddButton()
                .checkToastPaintingAdded(paintingName)
                .findPainting(paintingName)
                .checkPaintingTitleAndPhoto(paintingName, paintingImagePath)
                .findPaintingOnPageAndClick(paintingName)
                .checkElements()
                .checkPaintingTitle(paintingName)
                .checkPaintingArtist(artistName)
                .checkPaintingDescription(description)
                .checkPaintingPhoto(paintingImagePath);
    }

    @User
    @ApiLogin
    @Artist
    @Museum
    @Test
    void editPaintingShouldBeSuccess() {
        //TODO
    }
}
