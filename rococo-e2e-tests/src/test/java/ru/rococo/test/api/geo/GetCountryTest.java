package ru.rococo.test.api.geo;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.model.CountryJson;
import ru.rococo.service.impl.GeoGrpcClient;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.rococo.utils.Constants.COUNTRY_ID_NOT_FOUND;
import static ru.rococo.utils.Constants.INVALID_UUID;

public class GetCountryTest {

    private final GeoGrpcClient geoGrpcClient = new GeoGrpcClient();

    @Test
    void countryExistShouldBeSuccess() {
        List<CountryJson> countries = geoGrpcClient.allCountries(0, 1);

        CountryJson countryJson = geoGrpcClient.getCountry(String.valueOf(countries.getFirst().id()));

        assertNotNull(countryJson);
        assertNotNull(countryJson.id());
        assertNotNull(countryJson.name());

    }

    @Test
    void countryNotExistShouldBeFail() {
        String incorrectUuid = String.valueOf(UUID.randomUUID());

        assertThatThrownBy(() -> geoGrpcClient.getCountry(incorrectUuid))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(COUNTRY_ID_NOT_FOUND, incorrectUuid));
    }

    @Test
    void incorrectFormatUuidShouldBeFail() {
        String incorrectFormatUuid = "123";

        assertThatThrownBy(() -> geoGrpcClient.getCountry(incorrectFormatUuid))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(INVALID_UUID + incorrectFormatUuid);
    }
}
