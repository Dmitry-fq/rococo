package ru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import ru.rococo.jupiter.annotation.Artist;
import ru.rococo.model.ArtistJson;
import ru.rococo.service.ArtistClient;
import ru.rococo.service.impl.ArtistGrpcClient;
import ru.rococo.utils.DataUtils;

import static ru.rococo.utils.DataUtils.findPictureByPath;

public class ArtistExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ArtistExtension.class);

    private final ArtistClient artistClient = new ArtistGrpcClient();

    private static void setArtistToContext(ArtistJson artistJson) {
        final ExtensionContext context = TestMethodContextExtension.context();
        context.getStore(NAMESPACE).put(
                context.getUniqueId(),
                artistJson
        );
    }

    public static ArtistJson getArtistFromContext() {
        final ExtensionContext context = TestMethodContextExtension.context();
        return context.getStore(NAMESPACE).get(context.getUniqueId(), ArtistJson.class);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Artist.class)
                         .ifPresent(artistAnnotation -> {
                                     ArtistJson createdArtistJson = artistClient.addArtist(
                                             new ArtistJson(
                                                     null,
                                                     getNameByAnnotationOrRandom(artistAnnotation),
                                                     getBiographyByAnnotationOrRandom(artistAnnotation),
                                                     getPhotoByAnnotationOrEmpty(artistAnnotation)
                                             )
                                     );

                                     setArtistToContext(createdArtistJson);
                                 }
                         );
    }

    private String getNameByAnnotationOrRandom(Artist artistAnnotation) {
        if (artistAnnotation.name().isBlank()) {
            return DataUtils.randomArtistName();
        } else {
            return artistAnnotation.name();
        }
    }

    private String getBiographyByAnnotationOrRandom(Artist artistAnnotation) {
        if (artistAnnotation.name().isBlank()) {
            return DataUtils.randomText();
        } else {
            return artistAnnotation.biography();
        }
    }

    private String getPhotoByAnnotationOrEmpty(Artist artistAnnotation) {
        if (artistAnnotation.photo().isBlank()) {
            return artistAnnotation.photo();
        } else {
            return findPictureByPath(artistAnnotation.photo());
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(ArtistJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getArtistFromContext();
    }
}
