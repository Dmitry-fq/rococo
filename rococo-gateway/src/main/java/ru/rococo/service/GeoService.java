package ru.rococo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.rococo.model.CountryJson;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

@Component
public class GeoService {

    public @Nonnull Page<CountryJson> allCountries(@Nonnull Pageable pageable) {
        //TODO намеренно хардкожу для монолита
        List<CountryJson> countryList = List.of(
                new CountryJson(
                        UUID.randomUUID(),
                        "name1"
                ),
                new CountryJson(
                        UUID.randomUUID(),
                        "name2"
                )
        );

        return new PageImpl<>(countryList, pageable, countryList.size());
    }
}
