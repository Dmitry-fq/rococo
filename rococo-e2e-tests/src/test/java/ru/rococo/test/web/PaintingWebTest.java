package ru.rococo.test.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;
import ru.rococo.page.component.Header;

@WebTest
public class PaintingWebTest {

    private static final Config CFG = Config.getInstance();

    @User
    @ApiLogin
    @Artist
    @Museum
    @Test
    void addPaintingShouldBeSuccess() {
        Selenide.open(CFG.frontUrl(), Header.class)
                .clickPaintingButton()
                .checkElements()
                .clickAddPainting();
        //TODO
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
