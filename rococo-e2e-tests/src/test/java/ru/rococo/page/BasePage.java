package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public abstract class BasePage<T extends BasePage<T>> {

    private static final String PROFILE_REFRESHED = "Профиль обновлен";

    private static final String SESSION_COMPLETED = "Сессия завершена";

    private final SelenideElement toast = $x("//div[@data-testid='toast']");

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }

    @Step("Проверка тоста 'Профиль обновлен'")
    public T checkToastProfileRefreshed() {
        checkToast(PROFILE_REFRESHED);
        return self();
    }

    @Step("Проверка тоста 'Профиль обновлен'")
    public T checkToastSessionCompleted() {
        checkToast(SESSION_COMPLETED);
        return self();
    }

    @Step("Проверка тоста")
    private T checkToast(String elementText) {
        toast.shouldHave(text(elementText));
        return self();
    }
}
