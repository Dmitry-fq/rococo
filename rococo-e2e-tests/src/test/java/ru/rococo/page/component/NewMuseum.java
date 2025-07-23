package ru.rococo.page.component;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.MuseumPage;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class NewMuseum {

    private static final SelenideElement element = $x("//div[contains(@class, 'w-modal')]");

    private static final SelenideElement museumNameTitle = $x("//span[text()='Название музея']");

    private static final SelenideElement museumNameInput = $x("//input[@name='title']");

    private static final SelenideElement countryTitle = $x("//span[text()='Укажите страну']");

    private static final ElementsCollection countries = $$x("//select/option");

    private static final SelenideElement cityTitle = $x("//span[text()='Укажите город']");

    private static final SelenideElement cityInput = $x("//input[@name='city']");

    private static final SelenideElement museumPhotoTitle = $x("//span[contains(text(), 'Изображение музея')]");

    private static final SelenideElement museumPhotoInput = $x("//input[@placeholder='Изображение музея']");

    private static final SelenideElement descriptionTitle = $x("//span[text()='О музее']");

    private static final SelenideElement descriptionInput = $x("//textarea[@name='description']");

    private static final SelenideElement closeButton = $x("//button[text()='Закрыть']");

    private static final SelenideElement addButton = $x("//button[text()='Добавить']");

    @Nonnull
    @Step("Проверить элементы на странице")
    public NewMuseum checkElements() {
        element.shouldBe(visible);
        museumNameTitle.shouldBe(visible);
        countryTitle.shouldBe(visible);
        countries.shouldHave(sizeGreaterThan(0));
        cityTitle.shouldBe(visible);
        cityInput.shouldBe(visible);
        museumPhotoTitle.shouldBe(visible);
        museumPhotoInput.shouldBe(visible);
        descriptionInput.shouldBe(visible);
        closeButton.shouldBe(visible);
        addButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Установка нового названия музея")
    public NewMuseum setMuseumNameInput(String museumName) {
        museumNameInput.setValue(museumName);
        return this;
    }

    @Nonnull
    @Step("Проверка названия музея")
    public NewMuseum checkMuseumNameInput(String museumName) {
        museumNameTitle.shouldHave(value(museumName));
        return this;
    }

    @Nonnull
    @Step("Установка страны музея")
    public NewMuseum setCountry(String countryName) {
        countries.findBy(text(countryName))
                 .click();

        return this;
    }

    @Nonnull
    @Step("Установка города музея")
    public NewMuseum setCityInput(String cityName) {
        cityInput.setValue(cityName);
        return this;
    }

    @Nonnull
    @Step("Установка изображения музея")
    public NewMuseum setMuseumPhotoInput(String path) {
        museumPhotoInput.uploadFromClasspath(path);
        return this;
    }

    @Nonnull
    @Step("Установка описания музея")
    public NewMuseum setDescriptionInput(String text) {
        descriptionInput.setValue(text);
        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Закрыть'")
    public MuseumPage clickCloseButton() {
        closeButton.click();
        return new MuseumPage();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Добавить'")
    public MuseumPage clickAddButton() {
        addButton.click();
        return new MuseumPage();
    }
}
