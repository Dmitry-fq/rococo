package ru.rococo.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.ArtistPage;
import ru.rococo.page.LoginPage;
import ru.rococo.page.MainPage;
import ru.rococo.page.MuseumPage;
import ru.rococo.page.PaintingPage;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class Header {

    private static final SelenideElement mainButton = $x("//h1");

    private static final SelenideElement paintingsButton = $x("//a[contains(text(), 'Картины')]");

    private static final SelenideElement artistsButton = $x("//a[contains(text(), 'Картины')]");

    private static final SelenideElement museumsButton = $x("//a[contains(text(), 'Картины')]");

    private static final SelenideElement themeRadioButton = $x("//a[contains(text(), 'Картины')]");

    private static final SelenideElement enterButton = $x("//button[contains(text(), 'Войти')]");

    private static final SelenideElement avatarButton = $x("//figure[@data-testid='avatar']");

    @Step("Проверка элементов если пользователь авторизован")
    public void checkElementsWithAuthorization() {
        checkElements();
        checkAvatarButton();
    }

    @Step("Проверка элементов если пользователь не авторизован")
    public void checkElementsWithoutAuthorization() {
        checkElements();
        checkEnterButton();
    }

    private void checkElements() {
        mainButton.shouldBe(visible);
        paintingsButton.shouldBe(visible);
        artistsButton.shouldBe(visible);
        museumsButton.shouldBe(visible);
        themeRadioButton.shouldBe(visible);
    }

    @Step("Проверка что кнопка аватара отображается")
    public void checkAvatarButton() {
        avatarButton.shouldBe(visible);
    }

    @Nonnull
    @Step("Проверка аватара в хедере")
    public Header checkAvatarHeader(String imgPath) {
        SelenideElement actualAvatar = avatarButton.$x("./img");
        String expectedAvatar = DataUtils.getImageByPathOrEmpty(imgPath);

        actualAvatar.shouldHave(attribute("src", expectedAvatar));

        return this;
    }

    @Step("Проверка что кнопка входа отображается")
    public void checkEnterButton() {
        enterButton.shouldBe(visible);
    }

    @Nonnull
    @Step("Нажать на кнопку 'Войти'")
    public LoginPage clickEnterButton() {
        enterButton.click();
        return new LoginPage();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Войти'")
    public Profile clickAvatarButton() {
        avatarButton.click();
        return new Profile();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Главная страница'")
    public MainPage clickMainButton() {
        mainButton.click();
        return new MainPage();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Картины'")
    public PaintingPage clickPaintingButton() {
        paintingsButton.click();
        return new PaintingPage();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Художники'")
    public ArtistPage clickArtistButton() {
        artistsButton.click();
        return new ArtistPage();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Музеи'")
    public MuseumPage clickMuseumButton() {
        museumsButton.click();
        return new MuseumPage();
    }
}
