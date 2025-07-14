package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.component.Header;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends BasePage<MainPage> {

    protected final Header header = new Header();

    private final SelenideElement mainTitle = $x("//p[contains(text(), 'Ваши любимые картины и художники всегда рядом')]");

    private final SelenideElement paintingsButton = $x("//a[@href='/painting']/img");

    private final SelenideElement artistsButton = $x("//a[@href='/artist']/img");

    private final SelenideElement museumsButton = $x("//a[@href='/museum']/img");

    @Step("Проверка элементов страницы авторизованного пользователя")
    @Nonnull
    public MainPage checkElementsWithAuthorization() {
        header.checkElementsWithAuthorization();
        checkElements();
        return this;
    }

    @Step("Проверка элементов страницы неавторизованного пользователя")
    @Nonnull
    public MainPage checkElementsWithoutAuthorization() {
        header.checkElementsWithoutAuthorization();
        checkElements();
        return this;
    }

    private void checkElements() {
        mainTitle.shouldBe(visible);
        paintingsButton.shouldBe(visible);
        artistsButton.shouldBe(visible);
        museumsButton.shouldBe(visible);
    }

    @Step("Проверка что пользователь авторизован")
    @Nonnull
    public MainPage checkAuthorized() {
        header.checkAvatarButton();
        return this;
    }

    @Step("Проверка что пользователь не авторизован")
    @Nonnull
    public MainPage checkNotAuthorized() {
        header.checkEnterButton();
        return this;
    }

    @Step("Клик на кнопку 'Картины' ")
    @Nonnull
    public PaintingPage clickPaintingButton() {
        paintingsButton.click();
        return new PaintingPage();
    }

    @Step("Клик на кнопку 'Художники' ")
    @Nonnull
    public ArtistPage clickArtistButton() {
        artistsButton.click();
        return new ArtistPage();
    }

    @Step("Клик на кнопку 'Музеи' ")
    @Nonnull
    public MuseumPage clickMuseumButton() {
        museumsButton.click();
        return new MuseumPage();
    }
}
