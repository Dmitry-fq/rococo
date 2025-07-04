package ru.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ArtistPage extends BasePage<ArtistPage> {

    private final SelenideElement mainTitle = $x("//h2[contains(text(), 'Художники')]");

    private final SelenideElement addArtistButton = $x("//button[contains(text(), 'Добавить художника')]");

    private final SelenideElement searchInput = $x("//input[@type='search']");

    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");

    private final ElementsCollection artists = $$x("//div[@class='w-100']/ul/li");

}
