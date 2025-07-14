package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage<LoginPage> {

    private final SelenideElement mainTitle = $x("//h1");

    private final SelenideElement incorrectUserDataError = $x("//p[text() = 'Неверные учетные данные пользователя']");

    private final SelenideElement usernameTitle = $x("//span[contains(text(), 'Имя пользователя')]");

    private final SelenideElement usernameInput = $x("//input[@name = 'username']");

    private final SelenideElement passwordTitle = $x("//span[contains(text(), 'Пароль')]");

    private final SelenideElement passwordInput = $x("//input[@name = 'password']");

    private final SelenideElement hidePasswordButton = $x("//button[contains(@class, 'form__password-button')]");

    private final SelenideElement enterButton = $x("//button[@type='submit']");

    private final SelenideElement registerButton = $x("//a[@href='/register']");

    @Nonnull
    @Step("Проверка элементов на странице 'Логин'")
    public LoginPage checkElements() {
        mainTitle.shouldHave(text("Rococo"));
        usernameTitle.shouldBe(visible);
        usernameTitle.shouldBe(visible);
        usernameInput.shouldBe(visible);
        passwordTitle.shouldBe(visible);
        passwordInput.shouldBe(visible);
        enterButton.shouldBe(visible);
        registerButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Проверка ошибки 'Неверные учетные данные пользователя'")
    public LoginPage checkIncorrectUserDataError() {
        incorrectUserDataError.shouldBe(visible);
        return this;
    }

    @Nonnull
    @Step("Ввод имени пользователя")
    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Nonnull
    @Step("Ввод пароля")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Nonnull
    @Step("Нажатие на кнопку 'Скрыть пароль'")
    public LoginPage clickHidePasswordButton() {
        hidePasswordButton.click();
        return this;
    }

    @Nonnull
    @Step("Проверка что символы в поле 'Пароль' скрыты")
    public LoginPage checkHideTypeSymbolsInPasswordInput() {
        passwordInput.shouldHave(attribute("type", "password"));
        return this;
    }

    @Nonnull
    @Step("Проверка что символы в поле 'Пароль' не скрыты")
    public LoginPage checkNotHideTypeSymbolsInPasswordInput() {
        passwordInput.shouldHave(attribute("type", "text"));
        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Войти'")
    public MainPage clickEnterButton() {
        enterButton.click();
        return new MainPage();
    }

    @Nonnull
    @Step("Нажать на кнопку 'Зарегистрироваться'")
    public RegistrationPage clickRegisterButton() {
        registerButton.click();
        return new RegistrationPage();
    }

}
