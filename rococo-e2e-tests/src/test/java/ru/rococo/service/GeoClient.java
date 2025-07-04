package ru.rococo.service;

import ru.rococo.model.CountryJson;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface GeoClient {

    @Nonnull
    CountryJson getCountry(String id);

    @Nonnull
    CountryJson getCountryByName(String name);

    @Nonnull
    List<CountryJson> allCountries(int page, int size);
}
