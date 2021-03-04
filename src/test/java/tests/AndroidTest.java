package tests;

import com.codeborne.selenide.Configuration;
import drivers.MobileDriver;
import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.back;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static io.appium.java_client.MobileBy.AccessibilityId;
import static io.qameta.allure.Allure.step;


public class AndroidTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = MobileDriver.class.getName();
        Configuration.browserSize = null;
        Configuration.timeout = 10000;
    }

    @Test
    void searchTest() {
        open();

        back();
        $(AccessibilityId("Search Wikipedia")).click();
        $(MobileBy.id("org.wikipedia.alpha:id/search_src_text")).setValue("BrowserStack");
        $$(byClassName("android.widget.TextView")).shouldHave(sizeGreaterThan(0));
    }

    @Test
    void addLanguageTest() {
        open();
        step("Click 'Add Language' on start page", () -> {
            $(MobileBy.id("org.wikipedia.alpha:id/addLangContainer")).click();
        });

        step("Click 'Add Language' jn Wikipedia Languages Page", () -> {
            $(MobileBy.className("androidx.recyclerview.widget.RecyclerView"))
                    .$$(MobileBy.className("android.widget.LinearLayout"))
                    .get(2)
                    .click();
        });

        step("Choose spanish language and click", () -> {
            $(MobileBy.id("org.wikipedia.alpha:id/languages_list_recycler"))
                    .$$(MobileBy.className("android.widget.TextView"))
                    .get(1)
                    .click();
        });

        step("Check that spanish language is added", () -> {
            $(MobileBy.className("androidx.recyclerview.widget.RecyclerView"))
                    .$$(MobileBy.className("android.widget.LinearLayout")).get(2)
                    .$$(MobileBy.className("android.widget.TextView")).get(2)
                    .shouldHave(text("Español"));
        });
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
