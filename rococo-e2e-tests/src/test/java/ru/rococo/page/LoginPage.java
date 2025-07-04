package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage<LoginPage> {

    private final SelenideElement usernameTitle = $x("//span[contains(text(), 'Имя пользователя')]");

    private final SelenideElement usernameInput = $x("//input[@name = 'username']");

    private final SelenideElement passwordTitle = $x("//span[contains(text(), 'Пароль')]");

    private final SelenideElement passwordInput = $x("//input[@name = 'password']");

    private final SelenideElement enterButton = $x("//button[@type='submit']");

    private final SelenideElement registerButton = $x("//a[@href='/register']");

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
