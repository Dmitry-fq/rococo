package ru.rococo.service;

import ru.rococo.model.MuseumJson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface MuseumClient {

    @Nullable
    MuseumJson getMuseum(String id);

    @Nullable
    MuseumJson getMuseumByTitle(String title);

    @Nonnull
    List<MuseumJson> allMuseums(String title, int page, int size);

    @Nonnull
    MuseumJson updateMuseum(MuseumJson museumJson);

    @Nonnull
    MuseumJson addMuseum(MuseumJson museumJson);
}
