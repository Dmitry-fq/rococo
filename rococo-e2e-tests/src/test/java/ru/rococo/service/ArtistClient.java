package ru.rococo.service;

import ru.rococo.model.ArtistJson;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface ArtistClient {

    @Nonnull
    ArtistJson getArtist(String id);

    @Nullable
    ArtistJson getArtistByName(String name);

    List<ArtistJson> allArtists(String username, int page, int size);

    @Nonnull
    ArtistJson updateArtist(ArtistJson artistJson);

    @Nonnull
    ArtistJson addArtist(ArtistJson artistJson);
}
