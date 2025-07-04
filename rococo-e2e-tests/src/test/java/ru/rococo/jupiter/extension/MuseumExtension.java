package ru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.model.CountryJson;
import ru.rococo.model.GeoJson;
import ru.rococo.model.MuseumJson;
import ru.rococo.service.MuseumClient;
import ru.rococo.service.impl.MuseumGrpcClient;
import ru.rococo.utils.DataUtils;

import static ru.rococo.utils.DataUtils.findPictureByPath;
import static ru.rococo.utils.DataUtils.getNotBlankStringOrRandom;

public class MuseumExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(MuseumExtension.class);

    private final MuseumClient museumClient = new MuseumGrpcClient();

    private static void setMuseumToContext(MuseumJson museumJson) {
        final ExtensionContext context = TestMethodContextExtension.context();
        context.getStore(NAMESPACE).put(
                context.getUniqueId(),
                museumJson
        );
    }

    public static MuseumJson getMuseumFromContext() {
        final ExtensionContext context = TestMethodContextExtension.context();
        return context.getStore(NAMESPACE).get(context.getUniqueId(), MuseumJson.class);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Museum.class)
                         .ifPresent(museumAnnotation -> {
                                     MuseumJson createdMuseumJson = museumClient.addMuseum(
                                             new MuseumJson(
                                                     null,
                                                     getNotBlankStringOrRandom(museumAnnotation.title(), DataUtils::randomMuseumName),
                                                     getNotBlankStringOrRandom(museumAnnotation.description(), DataUtils::randomText),
                                                     getPhotoByAnnotationOrEmpty(museumAnnotation),
                                                     new GeoJson(
                                                             new CountryJson(
                                                                     null,
                                                                     getNotBlankStringOrRandom(
                                                                             museumAnnotation.country(),
                                                                             DataUtils::randomCountryName
                                                                     )
                                                             ),
                                                             getNotBlankStringOrRandom(museumAnnotation.city(), DataUtils::randomCityName)
                                                     )
                                             )
                                     );
                                     setMuseumToContext(createdMuseumJson);
                                 }
                         );
    }

    private String getPhotoByAnnotationOrEmpty(Museum museumAnnotation) {
        if (!museumAnnotation.photoPath().isBlank()) {
            return findPictureByPath(museumAnnotation.photoPath());
        } else {
            return "";
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(MuseumJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getMuseumFromContext();
    }
}
