package ru.rococo.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.LoginPage;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class Header {

    private static final SelenideElement mainTitle = $x("//h1");

    private static final SelenideElement paintingsButton = $x("//a[contains(text(), 'Картины')]");

    private static final SelenideElement artistsButton = $x("//a[contains(text(), 'Картины')]");

    private static final SelenideElement museumsButton = $x("//a[contains(text(), 'Картины')]");

    private static final SelenideElement themeRadioButton = $x("//a[contains(text(), 'Картины')]");

    private static final SelenideElement enterButton = $x("//button[contains(text(), 'Войти')]");

    private static final SelenideElement avatarButton = $x("//figure[@data-testid='avatar']");

    @Step("Кнопка аватара отображается")
    public void checkAuthorization() {
        avatarButton.shouldBe(visible);
    }

    public void checkEnterButton() {
        enterButton.shouldBe(visible);
    }

    @Nonnull
    @Step("Нажать на кнопку 'Войти'")
    public LoginPage clickEnterButton() {
        enterButton.click();

        return new LoginPage();
    }
}
