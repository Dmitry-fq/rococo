package ru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import ru.rococo.jupiter.annotation.Museum;
import ru.rococo.model.MuseumJson;
import ru.rococo.service.MuseumClient;
import ru.rococo.service.impl.MuseumGrpcClient;

import java.util.Objects;

public class MuseumExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(MuseumExtension.class);

    private static final MuseumClient museumClient = new MuseumGrpcClient();

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

    public static MuseumJson getMuseumByTitleOrCreate(Museum museumAnnotation) {
        MuseumJson museumJson = museumClient.getMuseumByTitle(museumAnnotation.title());
        return Objects.requireNonNullElseGet(museumJson, () ->
                museumClient.addMuseum(MuseumJson.fromAnnotation(museumAnnotation)));
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Museum.class)
                         .ifPresent(museumAnnotation -> {
                                     MuseumJson createdMuseumJson = getMuseumByTitleOrCreate(museumAnnotation);
                                     setMuseumToContext(createdMuseumJson);
                                 }
                         );
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
