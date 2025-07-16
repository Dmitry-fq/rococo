package ru.rococo.test.api.museum;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.jupiter.annotation.ApiTest;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.model.CountryJson;
import ru.rococo.model.GeoJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.service.impl.GeoGrpcClient;
import ru.rococo.service.impl.MuseumGrpcClient;
import ru.rococo.utils.DataUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.rococo.utils.Constants.COUNTRY_NAME_NOT_FOUND;
import static ru.rococo.utils.Constants.INVALID_UUID;
import static ru.rococo.utils.Constants.MUSEUM_ID_NOT_FOUND;

@ApiTest
public class UpdateMuseumTest {

    private final MuseumGrpcClient museumGrpcClient = new MuseumGrpcClient();

    private final GeoGrpcClient geoGrpcClient = new GeoGrpcClient();

    @Museum
    @Test
    void updateMuseumShouldBeSuccess(MuseumJson museum) {
        CountryJson country = geoGrpcClient.allCountries(0, 1).getFirst();
        MuseumJson newMuseum = new MuseumJson(
                museum.id(),
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

        MuseumJson museumJson = museumGrpcClient.updateMuseum(newMuseum);

        assertThat(museumJson).isEqualTo(newMuseum);
    }

    @Museum
    @Test
    void countryNotExistShouldBeFail(MuseumJson museum) {
        CountryJson country = geoGrpcClient.allCountries(0, 1).getFirst();
        String incorrectCountryName = DataUtils.randomCharactersInQuantity(10);
        MuseumJson newMuseum = new MuseumJson(
                museum.id(),
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

        assertThatThrownBy(() -> museumGrpcClient.updateMuseum(newMuseum))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(COUNTRY_NAME_NOT_FOUND, incorrectCountryName));
    }

    @Test
    void museumNotExistShouldBeFail() {
        UUID incorrectMuseumUuid = UUID.randomUUID();
        CountryJson country = geoGrpcClient.allCountries(0, 1).getFirst();
        MuseumJson newMuseum = new MuseumJson(
                incorrectMuseumUuid,
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

        assertThatThrownBy(() -> museumGrpcClient.updateMuseum(newMuseum))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(MUSEUM_ID_NOT_FOUND, incorrectMuseumUuid));
    }

    @Test
    void incorrectFormatUuidShouldBeFail() {
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

        assertThatThrownBy(() -> museumGrpcClient.updateMuseum(newMuseum))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(INVALID_UUID + "null");
    }
}
