package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.component.Header;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends BasePage<MainPage> {

    protected final Header header = new Header();

    private final SelenideElement mainTitle = $x("//p[contains(text(), 'Ваши любимые картины и художники всегда рядом')]");

    private final SelenideElement paintingsButton = $x("//a[@href='/painting']/img");

    private final SelenideElement artistsButton = $x("//a[@href='/artists']/img");

    private final SelenideElement museumsButton = $x("//a[@href='/museums']/img");

    @Step("Проверка что пользователь авторизован")
    @Nonnull
    public void checkAuthorization() {
        header.checkAuthorization();
    }
}
