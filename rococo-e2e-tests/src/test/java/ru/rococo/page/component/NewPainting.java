package ru.rococo.page.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.PaintingPage;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class NewPainting {

    private static final SelenideElement element = $x("//div[contains(@class, 'w-modal')]");

    private static final SelenideElement paintingNameTitle = $x("//span[text()='Название картины']");

    private static final SelenideElement paintingNameInput = $x("//input[@name='title']");

    private static final SelenideElement paintingPhotoTitle = $x("//span[contains(text(), 'Загрузите изображение картины')]");

    private static final SelenideElement paintingPhotoInput = $x("//input[@placeholder='Загрузите изображение картины']");

    private static final SelenideElement artistTitle = $x("//span[text()='Укажите автора картины']");

    private static final ElementsCollection artists = $$x("//select[@name = 'authorId']/option");

    private static final SelenideElement descriptionTitle = $x("//span[text()='Описание картины']");

    private static final SelenideElement descriptionInput = $x("//textarea[@name='description']");

    private static final SelenideElement museumTitle = $x("//span[text()='Укажите, где хранится оригинал']");

    private static final ElementsCollection museums = $$x("//select[@name = 'museumId']/option");

    private static final SelenideElement closeButton = $x("//button[text()='Закрыть']");

    private static final SelenideElement addButton = $x("//button[text()='Добавить']");

    @Nonnull
    @Step("Проверить элементы на странице")
    public NewPainting checkElements() {
        element.shouldBe(visible);
        paintingNameTitle.shouldBe(visible);
        paintingNameInput.shouldBe(visible);
        paintingPhotoTitle.shouldBe(visible);
        artistTitle.shouldBe(visible);
        artists.shouldHave(sizeGreaterThan(0));
        descriptionTitle.shouldBe(visible);
        descriptionInput.shouldBe(visible);
        museumTitle.shouldBe(visible);
        museums.shouldHave(sizeGreaterThan(0));
        closeButton.shouldBe(visible);
        addButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Установка нового названия картины")
    public NewPainting setPaintingNameInput(String paintingName) {
        paintingNameInput.setValue(paintingName);
        return this;
    }

    @Nonnull
    @Step("Проверка названия картины")
    public NewPainting checkPaintingNameInput(String paintingName) {
        paintingNameTitle.shouldHave(value(paintingName));
        return this;
    }

    @Nonnull
    @Step("Установка изображения музея")
    public NewPainting setPaintingPhotoInput(String path) {
        paintingPhotoInput.uploadFromClasspath(path);
        return this;
    }

    @Nonnull
    @Step("Установка художника картины")
    public NewPainting setArtist(String countryName) {
        artists.findBy(text(countryName))
               .click();

        return this;
    }

    @Nonnull
    @Step("Установка описания картины")
    public NewPainting setDescriptionInput(String text) {
        descriptionInput.setValue(text);
        return this;
    }

    @Nonnull
    @Step("Установка музея картины")
    public NewPainting setMuseum(String museumName) {
        museums.findBy(text(museumName))
               .click();

        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Закрыть'")
    public PaintingPage clickCloseButton() {
        closeButton.click();
        return new PaintingPage();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Добавить'")
    public PaintingPage clickAddButton() {
        addButton.click();
        return new PaintingPage();
    }
}
