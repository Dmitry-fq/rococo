package ru.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MuseumPage extends BasePage<MuseumPage> {

    private final SelenideElement mainTitle = $x("//h2[contains(text(), 'Музеи')]");

    private final SelenideElement addMuseumButton = $x("//button[contains(text(), 'Добавить музей')]");

    private final SelenideElement searchInput = $x("//input[@type='search']");

    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");

    private final ElementsCollection museums = $$x("//div[@class='w-100']/ul/li");

}
