package ru.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.component.NewPainting;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class PaintingPage extends BasePage<PaintingPage> {

    private final SelenideElement mainTitle = $x("//h2[contains(text(), 'Картины')]");

    private final SelenideElement addPaintingButton = $x("//button[contains(text(), 'Добавить картину')]");

    private final SelenideElement searchInput = $x("//input[@type='search']");

    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");

    private final ElementsCollection paintings = $$x("//div[@class='w-100']/ul/li");

    @Nonnull
    @Step("Проверка элементов на странице")
    public PaintingPage checkElements() {
        mainTitle.shouldBe(visible);
        searchInput.shouldBe(visible);
        searchButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Проверка кнопки 'Добавить картину'")
    public PaintingPage checkAddPaintingButtonNotVisible() {
        addPaintingButton.shouldNotBe(visible);
        return this;
    }

    @Nonnull
    @Step("Нажать на 'Добавить картину'")
    public NewPainting clickAddPaintingButton() {
        addPaintingButton.click();
        return new NewPainting();
    }

    @Nonnull
    @Step("Поиск картины")
    public PaintingPage findPainting(String paintingTitle) {
        searchInput.setValue(paintingTitle);
        searchButton.click();

        return this;
    }

    @Step("Проверить название и изображение картины")
    public PaintingPage checkPaintingTitleAndPhoto(String paintingTitle, String imgPath) {
        SelenideElement painting = paintings.findBy(text(paintingTitle));
        painting.shouldHave(text(paintingTitle));

        SelenideElement actualPhoto = painting.$x(".//img");
        String expectedPhoto = DataUtils.getImageByPathOrEmpty(imgPath);
        actualPhoto.shouldHave(attribute("src", expectedPhoto));

        return this;
    }

    @Step("Войти в профиль картины по имени")
    public PaintingProfilePage findPaintingOnPageAndClick(String paintingTitle) {
        paintings.findBy(text(paintingTitle)).click();
        return new PaintingProfilePage();
    }
}
