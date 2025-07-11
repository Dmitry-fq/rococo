package ru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;
import ru.rococo.model.UserJson;
import ru.rococo.page.component.Header;
import ru.rococo.utils.DataUtils;

import java.util.stream.Stream;

import static ru.rococo.utils.DataUtils.DEFAULT_PASSWORD;

@WebTest
public class RegistrationWebTest {

    private static final Config CFG = Config.getInstance();

    private final Header header = new Header();

    static Stream<String> shouldRegisterNewUser() {
        return Stream.of(DataUtils.randomCharactersInQuantity(3), DataUtils.randomCharactersInQuantity(49));
    }

    static Stream<String> shouldBeErrorWhenIncorrectUsername() {
        return Stream.of(DataUtils.randomCharactersInQuantity(2), DataUtils.randomCharactersInQuantity(51));
    }

    static Stream<String> shouldBeErrorWhenIncorrectPasswordAndRepeatPassword() {
        return Stream.of(DataUtils.randomCharactersInQuantity(2), DataUtils.randomCharactersInQuantity(13));
    }

    @ParameterizedTest(name = "[{index}] username: {arguments}")
    @MethodSource()
    void shouldRegisterNewUser(String username) {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .clickRegisterButton()
                .checkElements()
                .setUsername(username)
                .setPassword(DEFAULT_PASSWORD)
                .setRepeatPassword(DEFAULT_PASSWORD)
                .clickRegisterButton()
                .checkWelcomeMessage()
                .clickEnterButton();

        header.checkEnterButton();
    }

    @User
    @Test
    void shouldBeErrorWhenRegisterWithExistUsername(UserJson user) {
        String username = user.username();

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .clickRegisterButton()
                .checkElements()
                .setUsername(username)
                .setPassword(DEFAULT_PASSWORD)
                .setRepeatPassword(DEFAULT_PASSWORD)
                .clickRegisterButton()
                .checkUsernameExistError(username);
    }

    @ParameterizedTest(name = "[{index}] username: {arguments}")
    @MethodSource
    void shouldBeErrorWhenIncorrectUsername(String username) {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .clickRegisterButton()
                .checkElements()
                .setUsername(username)
                .setPassword(DEFAULT_PASSWORD)
                .setRepeatPassword(DEFAULT_PASSWORD)
                .clickRegisterButton()
                .checkIncorrectUsernameError();
    }

    @ParameterizedTest(name = "[{index}] password: {arguments}")
    @MethodSource()
    void shouldBeErrorWhenIncorrectPasswordAndRepeatPassword(String password) {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .clickRegisterButton()
                .checkElements()
                .setUsername(DataUtils.randomUsername())
                .setPassword(password)
                .setRepeatPassword(password)
                .clickRegisterButton()
                .checkIncorrectPasswordError()
                .checkIncorrectRepeatPasswordError();
    }

    @Test
    void shouldBeErrorWhenAllFieldsAreBlank() {
        String blankValue = "    ";

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .clickRegisterButton()
                .checkElements()
                .setUsername(blankValue)
                .setPassword(blankValue)
                .setRepeatPassword(blankValue)
                .clickRegisterButton()
                .checkUsernameIsBlankError()
                .checkPasswordIsBlankError()
                .checkRepeatPasswordIsBlankError();
    }
}
