package com.insider.tests;

import com.insider.pages.CareersPage;
import com.insider.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.insider.constants.TestGroups.CONTENT;
/**
 * Verifies that key sections are visible on the Careers page.
 */
public class CareersPageTest extends BaseTest {

    @Test(groups = {CONTENT})
    public void shouldDisplayKeySectionsOnCareersPage() {
        HomePage homePage = new HomePage(driver).openHomePage(BASE_URL);

        Assert.assertTrue(
                homePage.isHomePageLoaded(),
                "Home page is not loaded."
        );

        homePage.openCareersPageFromCompanyMenu();

        CareersPage careersPage = new CareersPage(driver);

        Assert.assertTrue(
                careersPage.isCareersPageLoaded(),
                "Careers page is not loaded."
        );

        Assert.assertTrue(
                careersPage.isLocationsSectionVisible(),
                "Locations section is not visible on Careers page."
        );

        Assert.assertTrue(
                careersPage.isTeamsSectionVisible(),
                "Teams section is not visible on Careers page."
        );

        Assert.assertTrue(
                careersPage.isLifeAtInsiderSectionVisible(),
                "Life at Insider section is not visible on Careers page."
        );
    }
}
