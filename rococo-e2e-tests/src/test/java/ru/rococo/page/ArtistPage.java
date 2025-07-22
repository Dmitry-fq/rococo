package ru.rococo.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.rococo.page.component.NewArtist;
import ru.rococo.utils.DataUtils;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ArtistPage extends BasePage<ArtistPage> {

    private final SelenideElement mainTitle = $x("//h2[contains(text(), 'Художники')]");

    private final SelenideElement addArtistButton = $x("//button[contains(text(), 'Добавить художника')]");

    private final SelenideElement searchInput = $x("//input[@type='search']");

    private final SelenideElement searchButton = $x("//img[@alt = 'Иконка поиска']");

    private final ElementsCollection artists = $$x("//div[@class='w-100']/ul/li");

    @Nonnull
    @Step("Проверка элементов на странице")
    public ArtistPage checkElements() {
        mainTitle.shouldBe(visible);
        searchInput.shouldBe(visible);
        searchButton.shouldBe(visible);

        return this;
    }

    @Nonnull
    @Step("Поиск художника")
    public ArtistPage findArtist(String artistName) {
        searchInput.setValue(artistName);
        searchButton.click();

        return this;
    }

    @Step("Войти в профиль художника по имени")
    public void findArtistOnPageAndClick(String artistName) {
        artists.findBy(text(artistName)).click();
    }

    @Step("Проверить имя и фото художника")
    public ArtistPage checkArtistNameAndPhoto(String artistName, String imgPath) {
        SelenideElement artist = artists.findBy(text(artistName));
        artist.shouldHave(text(artistName));

        SelenideElement actualPhoto = artist.$x(".//img");
        String expectedPhoto = DataUtils.getImageByPathOrEmpty(imgPath);
        actualPhoto.shouldHave(attribute("src", expectedPhoto));

        return this;
    }

    @Nonnull
    @Step("Проверка отсутствия кнопки 'Добавить художника'")
    public ArtistPage checkAddArtistButtonNotVisible() {
        addArtistButton.shouldNotBe(visible);
        return this;
    }

    @Nonnull
    @Step("Нажать на кнопку 'Добавить художника'")
    public NewArtist clickAddArtistButton() {
        addArtistButton.click();
        return new NewArtist();
    }

}
