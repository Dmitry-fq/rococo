package ru.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class PaintingPage extends BasePage<PaintingPage> {

    private final SelenideElement mainTitle = $x("//h2[contains(text(), 'Картины')]");

    private final SelenideElement addPaintingButton = $x("//button[contains(text(), 'Добавить картину')]");

    private final SelenideElement searchInput = $x("//input[@type='search']");

    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");

    private final ElementsCollection paintings = $$x("//div[@class='w-100']/ul/li");

}
