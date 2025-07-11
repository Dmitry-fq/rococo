package ru.rococo.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.MainPage;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class Profile {

    private static final SelenideElement element = $x("//div[contains(@class, 'w-modal')]");

    private static final SelenideElement mainTitle = $x("//header[contains(text(), 'Профиль')]");

    private static final SelenideElement avatar = element.$x(".//figure[@data-testid='avatar']");

    private static final SelenideElement logoutButton = $x("//button[text()='Выйти']");

    private static final SelenideElement usernameTitle = $x("//h4");

    private static final SelenideElement refreshPhotoTitle = $x("//span[contains(text(), 'Обновить фото профиля')]");

    private static final SelenideElement refreshPhotoInput = $x("//input[@placeholder='Обновить фото профиля']");

    private static final SelenideElement firstnameTitle = $x("//span[text()='Имя']");

    private static final SelenideElement firstnameInput = $x("//input[@name='firstname']");

    private static final SelenideElement surnameTitle = $x("//span[text()='Фамилия']");

    private static final SelenideElement surnameInput = $x("//input[@name='surname']");

    private static final SelenideElement closeButton = $x("//button[text()='Закрыть']");

    private static final SelenideElement refreshProfileButton = $x("//button[text()='Обновить профиль']");

    @Nonnull
    @Step("Проверить элементы на странице")
    public Profile checkElements(String username) {
        mainTitle.shouldBe(visible);
        avatar.shouldBe(visible);
        logoutButton.shouldBe(visible);
        usernameTitle.shouldHave(text("@" + username));
        refreshPhotoTitle.shouldBe(visible);
        refreshPhotoInput.shouldBe(visible);
        firstnameTitle.shouldBe(visible);
        firstnameInput.shouldBe(visible);
        surnameTitle.shouldBe(visible);
        surnameInput.shouldBe(visible);
        closeButton.shouldBe(visible);
        refreshProfileButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Установка нового аватара")
    public Profile setRefreshPhotoInput(String path) {
        refreshPhotoInput.uploadFromClasspath(path);
        return this;
    }

    @Nonnull
    public Profile checkFieldsAndAvatar(String imgPath, String firstname, String surname) {
        checkAvatarProfile(imgPath);
        checkFirstnameInput(firstname);
        checkSurnameInput(surname);

        return this;
    }

    @Nonnull
    @Step("Проверка имени")
    public Profile checkAvatarProfile(String imgPath) {
        SelenideElement actualAvatar = avatar.$x("./img");
        String expectedAvatar = DataUtils.getImageByPathOrEmpty(imgPath);
        actualAvatar.shouldHave(attribute("src", expectedAvatar));

        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Выйти'")
    public MainPage clickLogoutButton() {
        logoutButton.click();
        return new MainPage();
    }

    @Nonnull
    @Step("Установка нового имени")
    public Profile setFirstnameInput(String firstname) {
        firstnameInput.setValue(firstname);
        return this;
    }

    @Nonnull
    @Step("Проверка имени")
    public Profile checkFirstnameInput(String firstname) {
        firstnameInput.shouldHave(value(firstname));
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля 'Имя'")
    public Profile checkFirstnameInputError() {
        firstnameInput.shouldHave(attribute("class", "input input-error"));
        SelenideElement error = firstnameInput.$x("./following-sibling::span");
        error.shouldHave(text("Имя не может быть длиннее 255 символов"));
        return this;
    }

    @Nonnull
    @Step("Установка новой фамилии")
    public Profile setSurnameInput(String surname) {
        surnameInput.setValue(surname);
        return this;
    }

    @Nonnull
    @Step("Проверка фамилии")
    public Profile checkSurnameInput(String surname) {
        surnameInput.shouldHave(value(surname));
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля 'Фамилия'")
    public Profile checkSurnameInputError() {
        surnameInput.shouldHave(attribute("class", "input input-error"));
        SelenideElement error = surnameInput.$x("./following-sibling::span");
        error.shouldHave(text("Фамилия не может быть длиннее 255 символов"));
        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Закрыть'")
    public Profile clickCloseButton() {
        closeButton.click();
        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Обновить профиль'")
    public MainPage clickRefreshProfileButton() {
        refreshProfileButton.click();
        return new MainPage();
    }
}
