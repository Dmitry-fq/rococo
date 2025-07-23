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
public class MuseumWebTest {

    private static final Config CFG = Config.getInstance();

    @User
    @ApiLogin
    @Test
    void addMuseumShouldBeSuccess() {
        String museumTitle = DataUtils.randomArtistName();
        String country = DataUtils.defaultCountryName();
        String city = DataUtils.randomCityName();
        String museumImagePath = "img/museums/hermitage.jpg";
        String description = DataUtils.randomText();

        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickMuseumButton()
                .checkElements()
                .clickAddMuseumButton()
                .setMuseumNameInput(museumTitle)
                .setCountry(country)
                .setCityInput(city)
                .setMuseumPhotoInput(museumImagePath)
                .setDescriptionInput(description)
                .clickAddButton()
                .checkToastMuseumAdded(museumTitle)
                .findMuseum(museumTitle)
                .checkMuseumTitleAndPhoto(museumTitle, museumImagePath)
                .findMuseumOnPageAndClick(museumTitle)
                .checkMuseumTitle(museumTitle)
                .checkMuseumLocation(country, city)
                .checkMuseumDescription(description)
                .checkMuseumPhoto(museumImagePath);
    }

    @User
    @ApiLogin
    @Test
    void editMuseumShouldBeSuccess() {
        //TODO
    }

    @User
    @ApiLogin
    @Test
    void addMuseumWithIncorrectFieldShouldBeFail() {
        //TODO
    }

    @Test
    void museumShouldBeNotFound() {
        //TODO
    }
}
