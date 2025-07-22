package ru.rococo.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public abstract class BasePage<T extends BasePage<T>> {

    private static final String PROFILE_REFRESHED = "Профиль обновлен";

    private static final String SESSION_COMPLETED = "Сессия завершена";

    private static final String ARTIST_ADDED = "Добавлен художник: ";

    private static final String MUSEUM_ADDED = "Добавлен музей: ";

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

    @Step("Проверка тоста 'Добавлен художник")
    public T checkToastArtistAdded(String artistName) {
        checkToast(ARTIST_ADDED + artistName);
        return self();
    }

    @Step("Проверка тоста 'Добавлен музей")
    public T checkToastMuseumAdded(String museumTitle) {
        checkToast(MUSEUM_ADDED + museumTitle);
        return self();
    }

    @Step("Проверка тоста")
    private void checkToast(String elementText) {
        toast.shouldHave(text(elementText));
    }
}
