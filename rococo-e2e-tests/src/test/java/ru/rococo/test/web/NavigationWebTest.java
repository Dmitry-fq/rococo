package ru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.WebTest;
import ru.rococo.page.component.Header;

@WebTest
public class NavigationWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void headerNavigationShouldBeCorrect() {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickPaintingButton()
                .checkElements();

        new Header()
                .clickMainButton()
                .clickArtistButton()
                .checkElements();

        new Header()
                .clickMainButton()
                .clickMuseumButton()
                .checkElements();
    }
}
