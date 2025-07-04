package ru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.WebTest;
import ru.rococo.page.component.Header;
import ru.rococo.utils.DataUtils;

@WebTest
public class RegistrationWebTest {

    private static final Config CFG = Config.getInstance();

    private final Header header = new Header();

    @Test
    void shouldRegisterNewUser() {
        String username = DataUtils.randomUsername();
        String password = DataUtils.getDefaultPassword();

        Selenide.open(CFG.frontUrl(), Header.class)
                .clickEnterButton()
                .clickRegisterButton()
                .setUsername(username)
                .setPassword(password)
                .setRepeatPassword(password)
                .clickRegisterButton()
                .checkWelcomeMessage()
                .clickEnterButton();

        header.checkEnterButton();
    }
}
