package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MuseumProfilePage extends BasePage<MuseumProfilePage> {

    private final SelenideElement title = $x("//article//header");

    private final SelenideElement location = $x("//article//div[@class = 'text-center']");

    private final SelenideElement editButton = $x("//button[contains(text(), 'Редактировать')]");

    private final SelenideElement description = $x("//article/div/div/div[3]");

    private final SelenideElement photo = $x("//article//img");

    @Nonnull
    @Step("Проверка элементов на странице")
    public MuseumProfilePage checkElements() {
        title.shouldBe(visible);
        location.shouldBe(visible);
        editButton.shouldBe(visible);
        description.shouldBe(visible);
        photo.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Проверка названия музея")
    public MuseumProfilePage checkMuseumTitle(String museumName) {
        title.shouldHave(text(museumName));
        return this;
    }

    @Nonnull
    @Step("Проверка локации музея")
    public MuseumProfilePage checkMuseumLocation(String country, String city) {
        location.shouldHave(text(country + ", " + city));
        return this;
    }

    @Nonnull
    @Step("Проверка описания музея")
    public MuseumProfilePage checkMuseumDescription(String museumDescription) {
        description.shouldHave(text(museumDescription));
        return this;
    }

    @Nonnull
    @Step("Проверка фотографии музея")
    public MuseumProfilePage checkMuseumPhoto(String imgPath) {
        String expectedPhoto = DataUtils.getImageByPathOrEmpty(imgPath);
        photo.shouldHave(attribute("src", expectedPhoto));

        return this;
    }
}
