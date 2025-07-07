package ru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.model.UserJson;
import ru.rococo.service.UserdataClient;
import ru.rococo.service.impl.UserdataGrpcClient;
import ru.rococo.utils.DataUtils;

public class UserExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserExtension.class);

    private final UserdataClient userdataApiClient = new UserdataGrpcClient();

    public static void setUserToContext(UserJson testUser) {
        final ExtensionContext context = TestMethodContextExtension.context();
        context.getStore(NAMESPACE).put(
                context.getUniqueId(),
                testUser
        );
    }

    public static UserJson getUserFromContext() {
        final ExtensionContext context = TestMethodContextExtension.context();
        return context.getStore(NAMESPACE).get(context.getUniqueId(), UserJson.class);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                         .ifPresent(userAnnotation -> {
                             UserJson createdUser = userdataApiClient.createUser(
                                     getUsernameByAnnotationOrRandom(userAnnotation),
                                     userAnnotation.password());

                             setUserToContext(createdUser);
                         });
    }

    private String getUsernameByAnnotationOrRandom(User userAnnotation) {
        if (userAnnotation.username().isBlank()) {
            return DataUtils.randomUsername();
        } else {
            return userAnnotation.username();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getUserFromContext();
    }
}
