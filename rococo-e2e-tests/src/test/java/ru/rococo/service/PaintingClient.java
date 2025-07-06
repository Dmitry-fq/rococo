package ru.rococo.service;

import ru.rococo.model.PaintingJson;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface PaintingClient {

    @Nonnull
    PaintingJson getPainting(String id);

    @Nonnull
    List<PaintingJson> allPaintings(String title, int page, int size);

    @Nonnull
    List<PaintingJson> allPaintingsByAuthorId(String authorId, int page, int size);

    @Nonnull
    PaintingJson updatePainting(PaintingJson museumJson);

    @Nonnull
    PaintingJson addPainting(PaintingJson museumJson);
}
