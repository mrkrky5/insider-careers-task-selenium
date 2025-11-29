package com.insider.tests;

import static com.insider.constants.Urls.CAREERS_OPEN_POSITIONS_QA;
import static com.insider.constants.Urls.CAREERS_QA;
import static com.insider.constants.Urls.LEVER_DOMAIN;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import com.insider.pages.OpenPositionsPage;
import com.insider.utils.WindowUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.insider.constants.TestGroups.INTEGRATION;

/**
 * Verifies that QA jobs can be filtered by location and that each result
 * matches the expected department and location, and that "View Role"
 * opens the Lever application page in a new tab.
 */
public class OpenPositionsPageTest extends BaseTest {

    @Test(groups = {INTEGRATION})
    public void shouldFilterQaJobsByLocationAndDepartment() {
        // Open QA careers landing page
        driver.get(CAREERS_QA);

        OpenPositionsPage openPositionsPage = new OpenPositionsPage(driver);

        // Close global cookie banner if it is shown
        openPositionsPage.acceptCookiesIfPresent();

        // Click "See all QA jobs" on the QA page
        openPositionsPage.clickSeeAllQaJobsButton();

        // Verify that we are on QA Open Positions page
        String openPositionUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                openPositionUrl.startsWith(CAREERS_OPEN_POSITIONS_QA),
                "Expected to be on QA Open Positions page, but URL was: " + openPositionUrl
        );

        // Department filter auto-fills to "Quality Assurance" after a short delay.
        // Once it is set, select "Istanbul, Turkiye" from the location filter.
        openPositionsPage.selectLocationIstanbulTurkiye();

        // Get visible job cards after filters
        List<WebElement> jobCards = openPositionsPage.getVisibleJobCards();

        Assert.assertFalse(
                jobCards.isEmpty(),
                "Job list should not be empty after applying QA + Istanbul filters."
        );

        int cardIndex = 0;

        for (WebElement jobCard : jobCards) {
            boolean isMatching = openPositionsPage.isJobCardMatchingQaIstanbulCriteria(jobCard);

            String debugText =
                    "Job card does not match expected QA criteria. Card index: "
                            + cardIndex
                            + System.lineSeparator()
                            + "Card text:"
                            + System.lineSeparator()
                            + jobCard.getText();

            Assert.assertTrue(
                    isMatching,
                    debugText
            );

            cardIndex++;
        }

        // Remember current window and existing handles
        String originalWindowHandle = driver.getWindowHandle();
        Set<String> handlesBeforeClick = driver.getWindowHandles();

        // Click "View Role" on the first job card
        openPositionsPage.clickViewRoleOnFirstJobCard();

        // Wait for Lever tab to appear
        String leverWindowHandle = WindowUtils.waitForNewWindowHandle(
                driver,
                handlesBeforeClick,
                Duration.ofSeconds(5),
                Duration.ofMillis(250)
        );

        Assert.assertNotNull(
                leverWindowHandle,
                "A new window handle for Lever should appear after clicking 'View Role'."
        );

        // Switch to Lever tab
        driver.switchTo().window(leverWindowHandle);

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains(LEVER_DOMAIN),
                "Lever application URL should contain 'lever.co' but was: " + currentUrl
        );

        // Close Lever tab and return to original window
        driver.close();
        driver.switchTo().window(originalWindowHandle);
    }
}
