package ru.rococo.test.web;

import org.junit.jupiter.api.Test;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.jupiter.annotation.WebTest;

@WebTest
public class MuseumWebTest {

    private static final Config CFG = Config.getInstance();

    @User
    @ApiLogin
    @Test
    void addMuseumShouldBeSuccess() {
        //TODO
    }
}
