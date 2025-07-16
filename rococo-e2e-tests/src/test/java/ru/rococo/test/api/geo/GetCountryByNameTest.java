package ru.rococo.test.api.geo;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;
import ru.rococo.model.CountryJson;
import ru.rococo.service.impl.GeoGrpcClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.rococo.utils.Constants.COUNTRY_NAME_NOT_FOUND;

public class GetCountryByNameTest {

    private final GeoGrpcClient geoGrpcClient = new GeoGrpcClient();

    @Test
    void countryExistShouldBeSuccess() {
        List<CountryJson> country = geoGrpcClient.allCountries(1, 1);

        CountryJson countryJson = geoGrpcClient.getCountryByName(country.getFirst().name());

        assertNotNull(countryJson);
        assertThat(countryJson.id())
                .isNotNull();
        assertThat(countryJson.name())
                .isNotNull();
    }

    @Test
    void countryNotExistShouldBeFail() {
        String incorrectName = "123";

        assertThatThrownBy(() -> geoGrpcClient.getCountryByName(incorrectName))
                .isInstanceOf(StatusRuntimeException.class)
                .hasMessage(String.format(COUNTRY_NAME_NOT_FOUND, incorrectName));
    }
}
