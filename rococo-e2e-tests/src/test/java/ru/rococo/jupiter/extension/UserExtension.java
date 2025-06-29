package ru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import ru.rococo.data.UserEntity;
import ru.rococo.jupiter.annotation.User;
import ru.rococo.model.UserJson;
import ru.rococo.service.UsersClient;
import ru.rococo.service.impl.UserdataApiClient;
import ru.rococo.utils.DataUtils;

public class UserExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserExtension.class);

    private final UsersClient userdataApiClient = new UserdataApiClient();

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                         .ifPresent(userAnnotation -> {
                             if ("".equals(userAnnotation.username())) {
                                 final String username = DataUtils.randomUsername();
                                 UserEntity userEntity = new UserEntity();
                                 userEntity.setUsername(username);
                                 UserJson createdUserJson = userdataApiClient.createUser(
                                         username,
                                         DataUtils.getDefaultPassword());
                                 System.out.println(createdUserJson);
                             }
                         });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return null;
    }
}
