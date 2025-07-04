package ru.rococo.jupiter.extension;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserExtension implements AfterEachCallback {

    private static final String CHROME = "chrome";

    static {
        Configuration.timeout = 8000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = CHROME;
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--no-sandbox");

    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Selenide.closeWebDriver();
        }
    }
}
