package ru.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ArtistProfilePage extends BasePage<ArtistProfilePage> {

    private final SelenideElement name = $x("//article/header");

    private final SelenideElement biography = $x("//p");

    private final SelenideElement photo = $x("//figure/img");

    private final SelenideElement paintingListEmptyTitle = $x("//section/div/p");

    private final SelenideElement editButton = $x("//button[contains(text(), 'Редактировать')]");

    private final SelenideElement addPaintingButton = $x("//button[contains(text(), 'Добавить картину')]");

    private final ElementsCollection paintings = $$x("//div[@class='w-100']/ul/li");

    @Nonnull
    @Step("Проверка элементов на странице")
    public ArtistProfilePage checkElements() {
        name.shouldBe(visible);
        biography.shouldBe(visible);
        photo.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Проверка имени художника")
    public ArtistProfilePage checkArtistName(String artistName) {
        name.shouldHave(text(artistName));
        return this;
    }

    @Nonnull
    @Step("Проверка биографии художника")
    public ArtistProfilePage checkArtistBiography(String artistBiography) {
        biography.shouldHave(text(artistBiography));
        return this;
    }

    @Nonnull
    @Step("Проверка фотографии художника")
    public ArtistProfilePage checkArtistPhoto(String imgPath) {
        String expectedPhoto = DataUtils.getImageByPathOrEmpty(imgPath);
        photo.shouldHave(attribute("src", expectedPhoto));

        return this;
    }

    @Nonnull
    @Step("Проверка картины художника")
    public ArtistProfilePage checkArtistPainting(String paintingName, String imgPath) {
        SelenideElement painting = paintings.findBy(text(paintingName));
        painting.shouldHave(text(paintingName));

        SelenideElement actualPaintingImg = painting.$x(".//img");
        String expectedPaintingImg = DataUtils.getImageByPathOrEmpty(imgPath);
        actualPaintingImg.shouldHave(attribute("src", expectedPaintingImg));

        return this;
    }

    @Nonnull
    @Step("Проверка что список картин художника пуст")
    public ArtistProfilePage checkPaintingListIsEmpty() {
        paintingListEmptyTitle.shouldHave(text("Пока что список картин этого художника пуст."));

        return this;
    }
}
