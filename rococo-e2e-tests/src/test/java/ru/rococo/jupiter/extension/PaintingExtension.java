package ru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import ru.rococo.jupiter.annotation.Painting;
import ru.rococo.model.ArtistJson;
import ru.rococo.model.CountryJson;
import ru.rococo.model.GeoJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.model.PaintingJson;
import ru.rococo.service.ArtistClient;
import ru.rococo.service.MuseumClient;
import ru.rococo.service.PaintingClient;
import ru.rococo.service.impl.ArtistGrpcClient;
import ru.rococo.service.impl.MuseumGrpcClient;
import ru.rococo.service.impl.PaintingGrpcClient;
import ru.rococo.utils.DataUtils;

public class PaintingExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(PaintingExtension.class);

    private final PaintingClient paintingClient = new PaintingGrpcClient();

    private final ArtistClient artistClient = new ArtistGrpcClient();

    private final MuseumClient museumClient = new MuseumGrpcClient();

    private static void setPaintingToContext(PaintingJson paintingJson) {
        final ExtensionContext context = TestMethodContextExtension.context();
        context.getStore(NAMESPACE).put(
                context.getUniqueId(),
                paintingJson
        );
    }

    public static PaintingJson getPaintingFromContext() {
        final ExtensionContext context = TestMethodContextExtension.context();
        return context.getStore(NAMESPACE).get(context.getUniqueId(), PaintingJson.class);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Painting.class)
                         .ifPresent(paintingAnnotation -> {
                                     ArtistJson artistJson = getArtist(paintingAnnotation);
                                     MuseumJson museumJson = getMuseum(paintingAnnotation);

                                     PaintingJson createdPainting = paintingClient.addPainting(
                                             PaintingJson.fromAnnotation(paintingAnnotation, artistJson, museumJson)
                                     );
                                     setPaintingToContext(createdPainting);
                                 }
                         );
    }

    private ArtistJson getArtist(Painting paintingAnnotation) {
        if (paintingAnnotation.artist().length != 0) {
            return ArtistExtension.getArtistByNameOrCreate(paintingAnnotation.artist()[0]);
        } else {
            return artistClient.addArtist(
                    new ArtistJson(
                            null,
                            DataUtils.randomArtistName(),
                            DataUtils.randomText(),
                            ""
                    )
            );
        }
    }

    private MuseumJson getMuseum(Painting paintingAnnotation) {
        if (paintingAnnotation.museum().length != 0) {
            return MuseumExtension.getMuseumByTitleOrCreate(paintingAnnotation.museum()[0]);
        } else {
            return museumClient.addMuseum(
                    new MuseumJson(
                            null,
                            DataUtils.randomMuseumName(),
                            DataUtils.randomText(),
                            "",
                            new GeoJson(
                                    new CountryJson(
                                            null,
                                            DataUtils.defaultCountryName()
                                    ),
                                    DataUtils.randomCityName()
                            )
                    )
            );
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(PaintingJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getPaintingFromContext();
    }
}
