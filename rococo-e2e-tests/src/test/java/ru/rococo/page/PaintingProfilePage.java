package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class PaintingProfilePage extends BasePage<PaintingProfilePage> {

    private final SelenideElement title = $x("//article//header");

    private final SelenideElement artist = $x("//article//div[@class = 'text-center']");

    private final SelenideElement editButton = $x("//button[contains(text(), 'Редактировать')]");

    private final SelenideElement description = $x("//div[@class = 'm-4']");

    private final SelenideElement photo = $x("//article//img");

    @Nonnull
    @Step("Проверка элементов на странице")
    public PaintingProfilePage checkElements() {
        title.shouldBe(visible);
        artist.shouldBe(visible);
        editButton.shouldBe(visible);
        description.shouldBe(visible);
        photo.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Проверка названия картины")
    public PaintingProfilePage checkPaintingTitle(String paintingName) {
        title.shouldHave(text(paintingName));
        return this;
    }

    @Nonnull
    @Step("Проверка художника картины")
    public PaintingProfilePage checkPaintingArtist(String artistName) {
        artist.shouldHave(text(artistName));
        return this;
    }

    @Nonnull
    @Step("Проверка описания картины")
    public PaintingProfilePage checkPaintingDescription(String paintingDescription) {
        description.shouldHave(text(paintingDescription));
        return this;
    }

    @Nonnull
    @Step("Проверка фотографии картины")
    public PaintingProfilePage checkPaintingPhoto(String imgPath) {
        String expectedPhoto = DataUtils.getImageByPathOrEmpty(imgPath);
        photo.shouldHave(attribute("src", expectedPhoto));

        return this;
    }
}
