package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class RegistrationPage extends BasePage<RegistrationPage> {

    private static final String usernameIncorrectErrorText = "Allowed username length should be from 3 to 50 characters";

    private static final String passwordIncorrectErrorText = "Allowed password length should be from 3 to 12 characters";

    private final SelenideElement usernameTitle = $x("//span[contains(text(), 'Имя пользователя')]");

    private final SelenideElement usernameInput = $x("//input[@id = 'username']");

    private final SelenideElement usernameError = $x("//span[@class = 'form__error error__username']");

    private final SelenideElement passwordTitle = $x("//span[contains(text(), 'Пароль')]");

    private final SelenideElement passwordInput = $x("//input[@id = 'password']");

    private final SelenideElement passwordError = $x("//span[@class = 'form__error error__password']");

    private final SelenideElement repeatPasswordTitle = $x("//span[contains(text(), 'Повторите пароль')]");

    private final SelenideElement repeatPasswordInput = $x("//input[@id = 'passwordSubmit']");

    private final SelenideElement repeatPasswordError = $x("//span[@class = 'form__error error__passwordSubmit']");

    private final SelenideElement registerButton = $x("//button[@type='submit']");

    private final SelenideElement welcomeMessage = $x("//p[@class='form__subheader']");

    private final SelenideElement enterButton = $x("//a[contains(text(), 'Войти')]");

    @Nonnull
    @Step("Проверить элементы на странице")
    public RegistrationPage checkElements() {
        usernameTitle.shouldBe(visible);
        passwordTitle.shouldBe(visible);
        repeatPasswordTitle.shouldBe(visible);
        registerButton.shouldBe(visible);
        enterButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Ввод логина")
    public RegistrationPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля логина когда значение некорректно")
    public RegistrationPage checkIncorrectUsernameError() {
        usernameTitle.$x("./following-sibling::span[@class = 'error__indicator error__username']").shouldBe(exist);
        usernameError.shouldHave(text(usernameIncorrectErrorText));
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля логина когда пользователь уже существует")
    public RegistrationPage checkUsernameExistError(String username) {
        usernameTitle.$x("./following-sibling::span[@class = 'error__indicator error__username']").shouldBe(exist);
        usernameError.shouldHave(text(String.format("Username `%s` already exists", username)));
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля логина когда поле пустое")
    public RegistrationPage checkUsernameIsBlankError() {
        usernameTitle.$x("./following-sibling::span[@class = 'error__indicator error__username']").shouldBe(exist);
        usernameError.shouldHave(text("Username can not be blank"));
        return this;
    }

    @Nonnull
    @Step("Ввод пароля")
    public RegistrationPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля пароля когда значение некорректно")
    public RegistrationPage checkIncorrectPasswordError() {
        passwordTitle.$x("./following-sibling::span[@class = 'error__indicator error__password']").shouldBe(exist);
        passwordError.shouldHave(text(passwordIncorrectErrorText));
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля пароля когда поле пустое")
    public RegistrationPage checkPasswordIsBlankError() {
        passwordTitle.$x("./following-sibling::span[@class = 'error__indicator error__password']").shouldBe(exist);
        passwordError.shouldHave(text("Password can not be blank"));
        return this;
    }

    @Nonnull
    @Step("Ввод подтверждения пароля")
    public RegistrationPage setRepeatPassword(String password) {
        repeatPasswordInput.setValue(password);
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля подтверждения пароля когда значение некорректно")
    public RegistrationPage checkIncorrectRepeatPasswordError() {
        repeatPasswordTitle.$x("./following-sibling::span[@class = 'error__indicator error__passwordSubmit']")
                           .shouldBe(exist);
        repeatPasswordError.shouldHave(text(passwordIncorrectErrorText));
        return this;
    }

    @Nonnull
    @Step("Проверка ошибки поля подтверждения пароля когда поле пустое")
    public RegistrationPage checkRepeatPasswordIsBlankError() {
        repeatPasswordTitle.$x("./following-sibling::span[@class = 'error__indicator error__passwordSubmit']")
                           .shouldBe(exist);
        repeatPasswordError.shouldHave(text("Password submit can not be blank"));
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
    public LoginPage clickEnterButton() {
        enterButton.click();
        return new LoginPage();
    }
}
