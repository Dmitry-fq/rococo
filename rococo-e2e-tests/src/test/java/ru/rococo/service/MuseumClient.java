package ru.rococo.service;

import ru.rococo.model.MuseumJson;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface MuseumClient {

    @Nonnull
    MuseumJson getMuseum(String id);

    @Nonnull
    List<MuseumJson> allMuseums(String title, int page, int size);

    @Nonnull
    MuseumJson updateMuseum(MuseumJson museumJson);

    @Nonnull
    MuseumJson addMuseum(MuseumJson museumJson);
}
