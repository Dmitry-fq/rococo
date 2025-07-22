package ru.rococo.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.ArtistPage;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class NewArtist {

    private static final SelenideElement element = $x("//div[contains(@class, 'w-modal')]");

    private static final SelenideElement mainTitle = $x("//header[contains(text(), 'Новый художник')]");

    private static final SelenideElement descriptionTitle = $x("//article");

    private static final SelenideElement nameTitle = $x("//span[text()='Имя']");

    private static final SelenideElement artistNameInput = $x("//input[@name='name']");

    private static final SelenideElement artistPhotoTitle = $x("//span[contains(text(), 'Изображение художника')]");

    private static final SelenideElement artistPhotoInput = $x("//input[@placeholder='Изображение художника']");

    private static final SelenideElement biographyTitle = $x("//span[text()='Биография']");

    private static final SelenideElement biographyInput = $x("//textarea[@name='biography']");

    private static final SelenideElement closeButton = $x("//button[text()='Закрыть']");

    private static final SelenideElement addButton = $x("//button[text()='Добавить']");

    @Nonnull
    @Step("Проверить элементы на странице")
    public NewArtist checkElements() {
        element.shouldBe(visible);
        mainTitle.shouldBe(visible);
        descriptionTitle.shouldBe(visible);
        nameTitle.shouldBe(visible);
        artistNameInput.shouldBe(visible);
        artistPhotoTitle.shouldBe(visible);
        artistPhotoInput.shouldBe(visible);
        biographyTitle.shouldBe(visible);
        biographyInput.shouldBe(visible);
        closeButton.shouldBe(visible);
        addButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Установка нового имени")
    public NewArtist setArtistNameInput(String artistName) {
        artistNameInput.setValue(artistName);
        return this;
    }

    @Nonnull
    @Step("Проверка имени")
    public NewArtist checkArtistNameInput(String firstname) {
        artistNameInput.shouldHave(value(firstname));
        return this;
    }

    @Nonnull
    @Step("Установка нового изображения художника")
    public NewArtist setArtistPhotoInput(String path) {
        artistPhotoInput.uploadFromClasspath(path);
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля 'Имя'")
    public NewArtist checkArtistNameInputError() {
        artistNameInput.shouldHave(attribute("class", "input input-error"));
        SelenideElement error = artistNameInput.$x("./following-sibling::span");
        error.shouldHave(text("Имя не может быть длиннее 255 символов"));
        return this;
    }

    @Nonnull
    @Step("Установка новой биографии")
    public NewArtist setBiographyInput(String text) {
        biographyInput.setValue(text);
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля 'Биография'")
    public NewArtist checkBiographyInputError() {
        biographyInput.shouldHave(attribute("class", "input input-error"));
        SelenideElement error = biographyInput.$x("./following-sibling::span");
        error.shouldHave(text("Биография не может быть длиннее 2000 символов"));
        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Закрыть'")
    public ArtistPage clickCloseButton() {
        closeButton.click();
        return new ArtistPage();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Добавить'")
    public ArtistPage clickAddButton() {
        addButton.click();
        return new ArtistPage();
    }
}
