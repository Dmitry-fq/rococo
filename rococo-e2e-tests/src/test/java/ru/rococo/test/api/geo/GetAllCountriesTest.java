package ru.rococo.test.api.geo;

import org.junit.jupiter.api.Test;
import ru.rococo.model.CountryJson;
import ru.rococo.service.impl.GeoGrpcClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetAllCountriesTest {

    private final GeoGrpcClient geoGrpcClient = new GeoGrpcClient();

    @Test
    void sizeShouldBeCorrect() {
        int size = 10;
        List<CountryJson> country = geoGrpcClient.allCountries(0, size);

        assertThat(country)
                .hasSize(size)
                .allSatisfy(countryJson -> {
                    assertNotNull(countryJson.id());
                    assertNotNull(countryJson.name());
                });
    }
}
