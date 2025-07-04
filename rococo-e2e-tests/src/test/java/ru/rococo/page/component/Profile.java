package ru.rococo.page.component;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class Profile {

    private static final SelenideElement element = $x("//div[contains(@class, 'w-modal')]");

    private static final SelenideElement mainTitle = $x("//header[contains(text(), 'Профиль')]");

    private static final SelenideElement logoutButton = $x("//button[text()='Выйти']");

    private static final SelenideElement avatar = element.$x("//figure[@data-testid='avatar']");

    private static final SelenideElement refreshPhotoTitle = $x("//span[contains(text(), 'Обновить фото профиля')]");

    private static final SelenideElement refreshPhotoInput = $x("//input[@placeholder='Обновить фото профиля']");

    private static final SelenideElement firstnameTitle = $x("//span[text()='Имя']");

    private static final SelenideElement firstnameInput = $x("//input[@name='firstname']");

    private static final SelenideElement surnameTitle = $x("//span[text()='Фамилия']");

    private static final SelenideElement surnameInput = $x("//input[@name='surname']");

    private static final SelenideElement closeButton = $x("//button[text()='Закрыть']");

    private static final SelenideElement refreshProfileButton = $x("//button[text()='Обновить профиль']");

}
