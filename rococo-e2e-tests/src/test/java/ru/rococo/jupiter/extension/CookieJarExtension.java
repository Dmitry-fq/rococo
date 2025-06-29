package ru.rococo.jupiter.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ru.rococo.api.core.ThreadSafeCookieStore;

public class CookieJarExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        ThreadSafeCookieStore.INSTANCE.removeAll();
    }
}
