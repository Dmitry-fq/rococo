package ru.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.component.NewMuseum;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
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

    @Nonnull
    @Step("Нажать на кнопку 'Добавить музей'")
    public NewMuseum clickAddMuseumButton() {
        addMuseumButton.click();
        return new NewMuseum();
    }

    @Nonnull
    @Step("Поиск музея")
    public MuseumPage findMuseum(String museumTitle) {
        searchInput.setValue(museumTitle);
        searchButton.click();

        return this;
    }

    @Step("Войти в профиль музея по имени")
    public MuseumProfilePage findMuseumOnPageAndClick(String museumTitle) {
        museums.findBy(text(museumTitle)).click();
        return new MuseumProfilePage();
    }

    @Step("Проверить имя и фото художника")
    public MuseumPage checkMuseumTitleAndPhoto(String museumTitle, String imgPath) {
        SelenideElement artist = museums.findBy(text(museumTitle));
        artist.shouldHave(text(museumTitle));

        SelenideElement actualPhoto = artist.$x(".//img");
        String expectedPhoto = DataUtils.getImageByPathOrEmpty(imgPath);
        actualPhoto.shouldHave(attribute("src", expectedPhoto));

        return this;
    }
}
