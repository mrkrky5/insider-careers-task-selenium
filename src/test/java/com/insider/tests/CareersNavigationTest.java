package com.insider.tests;

import static com.insider.constants.Urls.CAREERS;

import com.insider.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.insider.constants.TestGroups.SMOKE;
import static com.insider.constants.TestGroups.NAVIGATION;

/**
 * Verifies that the user can navigate to the Careers page via the Company menu.
 */
public class CareersNavigationTest extends BaseTest {

    @Test(groups = {SMOKE, NAVIGATION})
    public void shouldNavigateToCareersPageFromCompanyMenu() {
        HomePage homePage = new HomePage(driver).openHomePage(BASE_URL);

        Assert.assertTrue(
                homePage.isHomePageLoaded(),
                "Home page is not loaded."
        );

        homePage.openCareersPageFromCompanyMenu();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.startsWith(CAREERS),
                "Not navigated to Careers page. Current URL: " + currentUrl
        );
    }
}
