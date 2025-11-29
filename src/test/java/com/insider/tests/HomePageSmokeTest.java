package com.insider.tests;

import java.util.Locale;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.insider.constants.TestGroups.SMOKE;

/**
 * Simple smoke test that verifies the Insider home page can be opened.
 */
public class HomePageSmokeTest extends BaseTest {

    @Test(groups = {SMOKE})
    public void shouldOpenInsiderHomePage() {
        driver.get(BASE_URL);

        String pageTitle = driver.getTitle();
        String pageTitleLower = pageTitle.toLowerCase(Locale.ROOT);

        Assert.assertTrue(
                pageTitleLower.contains("insider"),
                "Home page title does not contain 'Insider'. Actual title: " + pageTitle
        );
    }
}
