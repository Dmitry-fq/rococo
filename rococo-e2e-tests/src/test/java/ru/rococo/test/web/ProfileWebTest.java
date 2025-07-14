package ru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;
import ru.rococo.model.UserJson;
import ru.rococo.page.component.Header;
import ru.rococo.page.component.Profile;
import ru.rococo.utils.DataUtils;

@WebTest
public class ProfileWebTest {

    private static final Config CFG = Config.getInstance();

    @User
    @ApiLogin
    @Test
    void profileInfoShouldBeAdded(UserJson user) {
        String username = user.username();
        String imagePath = "img/artists/botticelli.jpg";
        String firstname = DataUtils.randomUsername();
        String surname = DataUtils.randomSurname();

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickAvatarButton()
                .checkElements(username)
                .setRefreshPhotoInput(imagePath)
                .setFirstnameInput(firstname)
                .setSurnameInput(surname)
                .clickRefreshProfileButton()
                .checkElementsWithAuthorization()
                .checkToastProfileRefreshed();

        new Header()
                .checkAvatarHeader(imagePath)
                .clickAvatarButton()
                .checkElements(username)
                .checkFieldsAndAvatar(imagePath, firstname, surname);

    }

    @User
    @ApiLogin
    @Test
    void profileInfoShouldBeChanged(UserJson user) {
        String username = user.username();
        String existingImagePath = "img/artists/botticelli.jpg";
        String existingFirstname = DataUtils.randomUsername();
        String existingSurname = DataUtils.randomSurname();

        String newImagePath = "img/artists/da_vinci.jpg";
        String newFirstname = DataUtils.randomUsername();
        String newSurname = DataUtils.randomSurname();

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickAvatarButton()
                .checkElements(username)
                .setRefreshPhotoInput(existingImagePath)
                .setFirstnameInput(existingFirstname)
                .setSurnameInput(existingSurname)
                .clickRefreshProfileButton()
                .checkElementsWithAuthorization()
                .checkToastProfileRefreshed();

        new Header()
                .clickAvatarButton()
                .checkElements(username)
                .setRefreshPhotoInput(newImagePath)
                .setFirstnameInput(newFirstname)
                .setSurnameInput(newSurname)
                .clickRefreshProfileButton()
                .checkElementsWithAuthorization()
                .checkToastProfileRefreshed();

        new Header()
                .checkAvatarHeader(newImagePath)
                .clickAvatarButton()
                .checkElements(username)
                .checkFieldsAndAvatar(newImagePath, newFirstname, newSurname);
    }

    @User
    @ApiLogin
    @Test
    void profileInfoShouldNotBeChanged(UserJson user) {
        String username = user.username();
        String imagePath = "img/artists/botticelli.jpg";
        String firstname = DataUtils.randomUsername();
        String surname = DataUtils.randomSurname();

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickAvatarButton()
                .checkElements(username)
                .setRefreshPhotoInput(imagePath)
                .setFirstnameInput(firstname)
                .setSurnameInput(surname)
                .clickRefreshProfileButton()
                .checkElementsWithAuthorization()
                .checkToastProfileRefreshed();

        new Header()
                .clickAvatarButton()
                .clickCloseButton();

        new Header()
                .checkAvatarHeader(imagePath)
                .clickAvatarButton()
                .checkElements(username)
                .checkFieldsAndAvatar(imagePath, firstname, surname);
    }

    @User
    @ApiLogin
    @Test
    void shouldBeValidationErrors(UserJson user) {
        String username = user.username();
        String imagePath = "img/artists/botticelli.jpg";
        String firstname = DataUtils.randomUsername();
        String surname = DataUtils.randomSurname();

        int incorrectSymbolQuantity = 256;
        String incorrectFirstname = DataUtils.randomCharactersInQuantity(incorrectSymbolQuantity);
        String incorrectSurname = DataUtils.randomCharactersInQuantity(incorrectSymbolQuantity);

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickAvatarButton()
                .checkElements(username)
                .setRefreshPhotoInput(imagePath)
                .setFirstnameInput(firstname)
                .setSurnameInput(surname)
                .clickRefreshProfileButton()
                .checkElementsWithAuthorization()
                .checkToastProfileRefreshed();

        new Header()
                .clickAvatarButton()
                .checkElements(username)
                .setFirstnameInput(incorrectFirstname)
                .setSurnameInput(incorrectSurname)
                .clickRefreshProfileButton();

        new Profile()
                .checkFirstnameInputError()
                .checkSurnameInputError()
                .clickCloseButton();

        new Header()
                .checkAvatarHeader(imagePath)
                .clickAvatarButton()
                .checkElements(username)
                .checkFieldsAndAvatar(imagePath, firstname, surname);
    }
}
