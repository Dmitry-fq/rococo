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
import ru.rococo.service.impl.UserdataGrpcClient;
import ru.rococo.utils.DataUtils;

import static ru.rococo.utils.DataUtils.DEFAULT_PASSWORD;

public class ApiLoginExtension implements BeforeTestExecutionCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ApiLoginExtension.class);

    private static final Config CFG = Config.getInstance();

    private final AuthApiClient authApiClient = new AuthApiClient();

    private final UserdataGrpcClient userdataGrpcClient = new UserdataGrpcClient();

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
                             final UserJson userJson;
                             if (apiLogin.username().isBlank()) {
                                 if (UserExtension.getUserFromContext() == null) {
                                     userJson = userdataGrpcClient.createUser(
                                             DataUtils.randomUsername(),
                                             DEFAULT_PASSWORD
                                     );
                                 } else {
                                     userJson = UserExtension.getUserFromContext();
                                 }
                             } else {
                                 userJson = userdataGrpcClient.getUser(apiLogin.username());
                             }

                             setTokenToContext(userJson.username());
                             setTokenInBrowser();
                         });
    }

    private void setTokenToContext(String username) {
        final String token = authApiClient.login(username, DEFAULT_PASSWORD);
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
