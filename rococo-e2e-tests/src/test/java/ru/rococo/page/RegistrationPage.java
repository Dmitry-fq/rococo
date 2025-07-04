package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class RegistrationPage extends BasePage<RegistrationPage> {

    private final SelenideElement usernameTitle = $x("//span[contains(text(), 'Имя пользователя')]");

    private final SelenideElement usernameInput = $x("//input[@id = 'username']");

    private final SelenideElement passwordTitle = $x("//span[contains(text(), 'Пароль')]");

    private final SelenideElement passwordInput = $x("//input[@id = 'password']");

    private final SelenideElement repeatPasswordTitle = $x("//span[contains(text(), 'Повторите пароль')]");

    private final SelenideElement repeatPasswordInput = $x("//input[@id = 'passwordSubmit']");

    private final SelenideElement registerButton = $x("//button[@type='submit']");

    private final SelenideElement welcomeMessage = $x("//p[@class='form__subheader']");

    private final SelenideElement enterButton = $x("//a[@class='form__submit']");

    @Nonnull
    @Step("Ввод логина")
    public RegistrationPage setUsername(String username) {
        usernameInput.setValue(username);

        return this;
    }

    @Nonnull
    @Step("Ввод пароля")
    public RegistrationPage setPassword(String password) {
        passwordInput.setValue(password);

        return this;
    }

    @Nonnull
    @Step("Ввод подтверждения пароля")
    public RegistrationPage setRepeatPassword(String password) {
        repeatPasswordInput.setValue(password);

        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Зарегистрироваться'")
    public RegistrationPage clickRegisterButton() {
        registerButton.click();

        return new RegistrationPage();
    }

    @Nonnull
    @Step("Проверка приветственного сообщения")
    public RegistrationPage checkWelcomeMessage() {
        welcomeMessage.shouldHave(text("Добро пожаловать в Rococo"));

        return this;
    }

    @Step("Нажать на кнопку 'Войти в систему'")
    public void clickEnterButton() {
        enterButton.click();
    }
}
