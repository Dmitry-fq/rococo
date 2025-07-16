package ru.rococo.test.api.museum;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.model.CountryJson;
import ru.rococo.model.GeoJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.service.impl.GeoGrpcClient;
import ru.rococo.service.impl.MuseumGrpcClient;
import ru.rococo.utils.DataUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static ru.rococo.utils.Constants.COUNTRY_NAME_NOT_FOUND;

@ApiTest
public class AddMuseumTest {

    private final MuseumGrpcClient museumGrpcClient = new MuseumGrpcClient();

    private final GeoGrpcClient geoGrpcClient = new GeoGrpcClient();

    @Test
    void addMuseumShouldBeSuccess() {
        CountryJson country = geoGrpcClient.allCountries(0, 1).getFirst();
        MuseumJson newMuseum = new MuseumJson(
                null,
                DataUtils.randomMuseumName(),
                DataUtils.randomText(),
                DataUtils.getImageByPathOrEmpty("img/museums/hermitage.jpg"),
                new GeoJson(
                        new CountryJson(
                                country.id(),
                                country.name()
                        ),
                        DataUtils.randomCityName()
                )
        );

        MuseumJson addedMuseumJson = museumGrpcClient.addMuseum(newMuseum);
        MuseumJson actualMuseumJson = museumGrpcClient.getMuseum(String.valueOf(addedMuseumJson.id()));

        assertSoftly(softly -> {
            softly.assertThat(actualMuseumJson.title()).isEqualTo(newMuseum.title());
            softly.assertThat(actualMuseumJson.description()).isEqualTo(newMuseum.description());
            softly.assertThat(actualMuseumJson.photo()).isEqualTo(newMuseum.photo());
            softly.assertThat(actualMuseumJson.geo()).isEqualTo(newMuseum.geo());
        });
    }

    @Test
    void countryNotExistShouldBeFail() {
        CountryJson country = geoGrpcClient.allCountries(0, 1).getFirst();
        String incorrectCountryName = DataUtils.randomCharactersInQuantity(10);
        MuseumJson newMuseum = new MuseumJson(
                null,
                DataUtils.randomMuseumName(),
                DataUtils.randomText(),
                DataUtils.getImageByPathOrEmpty("img/museums/hermitage.jpg"),
                new GeoJson(
                        new CountryJson(
                                country.id(),
                                incorrectCountryName
                        ),
                        DataUtils.randomCityName()
                )
        );

        assertThatThrownBy(() -> museumGrpcClient.addMuseum(newMuseum))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(COUNTRY_NAME_NOT_FOUND, incorrectCountryName));
    }
}
