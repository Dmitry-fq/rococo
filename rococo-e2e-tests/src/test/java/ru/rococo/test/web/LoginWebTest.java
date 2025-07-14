package ru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;
import ru.rococo.model.UserJson;
import ru.rococo.page.LoginPage;
import ru.rococo.page.MainPage;
import ru.rococo.page.component.Header;

import static ru.rococo.utils.DataUtils.DEFAULT_PASSWORD;
import static ru.rococo.utils.DataUtils.randomCharactersInQuantity;
import static ru.rococo.utils.DataUtils.randomUsername;

@WebTest
public class LoginWebTest {

    private static final Config CFG = Config.getInstance();

    @User
    @Test
    void shouldBeSuccessLogin(UserJson user) {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .checkElements()
                .setUsername(user.username())
                .setPassword(DEFAULT_PASSWORD)
                .clickEnterButton()
                .checkAuthorized();
    }

    @User
    @Test
    void shouldBeSuccessLoginFromRegisterPage(UserJson user) {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .clickRegisterButton()
                .checkElements()
                .clickEnterButton()
                .setUsername(user.username())
                .setPassword(DEFAULT_PASSWORD)
                .clickEnterButton()
                .checkAuthorized();
    }

    @User
    @ApiLogin
    @Test
    void shouldBeSuccessLogout(UserJson user) {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickAvatarButton()
                .checkElements(user.username())
                .clickLogoutButton()
                .checkElementsWithoutAuthorization()
                .checkToastSessionCompleted()
                .checkNotAuthorized();
    }

    @Test
    void whenIncorrectUsernameShouldBeError() {
        String incorrectUsername = randomUsername();

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .checkElements()
                .setUsername(incorrectUsername)
                .setPassword(DEFAULT_PASSWORD)
                .clickEnterButton();

        new LoginPage()
                .checkIncorrectUserDataError();
    }

    @User
    @Test
    void whenIncorrectPasswordShouldBeError(UserJson user) {
        String incorrectPassword = randomCharactersInQuantity(10);

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .checkElements()
                .setUsername(user.username())
                .setPassword(incorrectPassword)
                .clickEnterButton();

        new LoginPage()
                .checkIncorrectUserDataError();
    }

    @Test
    void symbolsInPasswordTypeShouldBeHide() {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .checkElements()
                .setPassword(DEFAULT_PASSWORD)
                .checkHideTypeSymbolsInPasswordInput()
                .clickHidePasswordButton()
                .checkNotHideTypeSymbolsInPasswordInput()
                .clickHidePasswordButton()
                .checkHideTypeSymbolsInPasswordInput();
    }

    @Test
    void notAuthorizedUserShouldCanNotAddPainting() {
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickPaintingButton()
                .checkElements()
                .checkAddPaintingButtonNotVisible();
    }

    @Test
    void notAuthorizedUserShouldCanNotAddArtist() {
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickArtistButton()
                .checkElements()
                .checkAddArtistButtonNotVisible();
    }

    @Test
    void notAuthorizedUserShouldCanNotAddMuseum() {
        Selenide.open(CFG.frontUrl(), MainPage.class)
                .clickMuseumButton()
                .checkElements()
                .checkAddMuseumButtonNotVisible();
    }
}
