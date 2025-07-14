package ru.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MuseumPage extends BasePage<MuseumPage> {

    private final SelenideElement mainTitle = $x("//h2[contains(text(), 'Музеи')]");

    private final SelenideElement addMuseumButton = $x("//button[contains(text(), 'Добавить музей')]");

    private final SelenideElement searchInput = $x("//input[@type='search']");

    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");

    private final ElementsCollection museums = $$x("//div[@class='w-100']/ul/li");

    @Nonnull
    @Step("Проверка элементов на странице")
    public MuseumPage checkElements() {
        mainTitle.shouldBe(visible);
        searchInput.shouldBe(visible);
        searchButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Проверка отсутствия кнопки 'Добавить музей'")
    public MuseumPage checkAddMuseumButtonNotVisible() {
        addMuseumButton.shouldNotBe(visible);
        return this;
    }
}
