package ru.rococo.jupiter.extension;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;
import org.openqa.selenium.Cookie;
import ru.rococo.api.core.ThreadSafeCookieStore;
import ru.rococo.config.Config;
import ru.rococo.jupiter.annotation.ApiLogin;
import ru.rococo.model.UserJson;
import ru.rococo.page.MainPage;
import ru.rococo.service.impl.AuthApiClient;
import ru.rococo.utils.DataUtils;

public class ApiLoginExtension implements BeforeTestExecutionCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ApiLoginExtension.class);

    private static final Config CFG = Config.getInstance();

    private final AuthApiClient authApiClient = new AuthApiClient();

    public static String getToken() {
        return TestMethodContextExtension.context().getStore(NAMESPACE).get("token", String.class);
    }

    public static void setToken(String token) {
        TestMethodContextExtension.context().getStore(NAMESPACE).put("token", token);
    }

    public static Cookie getJsessionIdCookie() {
        return new Cookie(
                "JSESSIONID",
                ThreadSafeCookieStore.INSTANCE.cookieValue("JSESSIONID")
        );
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), ApiLogin.class)
                         .ifPresent(apiLogin -> {
                             final UserJson userFromUserExtension = UserExtension.getUserFromContext();
                             if (apiLogin.username().isBlank() || apiLogin.password().isBlank()) {
                                 if (userFromUserExtension == null) {
                                     throw new IllegalStateException("Аннотация @User должна присутствовать," +
                                             " если в @ApiLogin username и password не заполнены");
                                 }
                             } else {
                                 if (userFromUserExtension == null) {
                                     throw new IllegalStateException("Аннотация @User не должна присутствовать," +
                                             " если в @ApiLogin заполнены username и password");
                                 }
                             }
                             setTokenToContext(userFromUserExtension);
                             setTokenInBrowser();
                         });
    }

    private void setTokenToContext(UserJson user) {
        final String token = authApiClient.login(user.username(), DataUtils.getDefaultPassword());
        setToken(token);
    }

    private void setTokenInBrowser() {
        Selenide.open(CFG.frontUrl());
        Selenide.localStorage().setItem("id_token", getToken());
        WebDriverRunner.getWebDriver().manage().addCookie(
                getJsessionIdCookie()
        );
        Selenide.open(CFG.frontUrl(), MainPage.class).checkAuthorization();
    }
}
