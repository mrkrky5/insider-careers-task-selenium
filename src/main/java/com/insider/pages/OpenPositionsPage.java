package com.insider.pages;

import java.util.List;

import com.insider.enums.Department;
import com.insider.enums.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page object for QA Open Positions with filters and job cards.
 */
public class OpenPositionsPage extends BasePage {

    // Expected values used in assertions
    private static final Department EXPECTED_DEPARTMENT = Department.QUALITY_ASSURANCE;
    private static final Location EXPECTED_LOCATION = Location.ISTANBUL_TURKIYE;

    // QA careers page – "See all QA jobs" CTA
    private static final By SEE_ALL_QA_JOBS_BUTTON =
            By.xpath("//a[contains(@href,'/careers/open-positions') and contains(.,'See all QA jobs')]");

    // Filters on the open-positions page
    private static final By DEPARTMENT_FILTER_DROPDOWN =
            By.id("select2-filter-by-department-container");

    private static final By LOCATION_FILTER_DROPDOWN =
            By.id("select2-filter-by-location-container");

    // Location option for "Istanbul, Turkiye"
    private static final By ISTANBUL_TURKIYE_OPTION =
            By.xpath("//li[contains(@class,'select2-results__option') and normalize-space()='Istanbul, Turkiye']");

    // Job list and cards
    private static final By JOB_LIST_SECTION =
            By.cssSelector("section#career-position-list");

    private static final By JOB_LIST_WRAPPER =
            By.id("jobs-list");

    private static final By JOB_CARD =
            By.cssSelector("div.position-list-item-wrapper");

    private static final By JOB_CARD_DEPARTMENT =
            By.cssSelector("span.position-department");

    private static final By JOB_CARD_LOCATION =
            By.cssSelector("div.position-location");

    // Lever "View Role" link inside a card
    private static final By JOB_CARD_VIEW_ROLE_BUTTON =
            By.cssSelector("a[href*='lever.co']");

    private final Actions actions;

    public OpenPositionsPage(final WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
    }

    /**
     * Clicks "See all QA jobs" on the QA careers page and waits for the list.
     */
    public void clickSeeAllQaJobsButton() {
        WebElement seeAllQaJobsButton =
                wait.until(ExpectedConditions.elementToBeClickable(SEE_ALL_QA_JOBS_BUTTON));

        seeAllQaJobsButton.click();

        // Ensure the initial list and cards are rendered
        waitUntilJobCardsVisible();
    }

    /**
     * Waits until the department filter shows "Quality Assurance".
     * This is required because the text is set asynchronously by JS.
     */
    public void waitForDepartmentFilterToBeQualityAssurance() {
        try {
            String expectedText = EXPECTED_DEPARTMENT.getDisplayName();

            wait.until(
                    ExpectedConditions.textToBePresentInElementLocated(
                            DEPARTMENT_FILTER_DROPDOWN,
                            expectedText
                    )
            );

            // Simple debug log for troubleshooting if needed
            String deptText = driver.findElement(DEPARTMENT_FILTER_DROPDOWN)
                    .getText()
                    .trim();
            System.out.println("DEBUG - Department filter text after wait: '" + deptText + "'");
        } catch (TimeoutException timeoutException) {
            String currentText;
            try {
                currentText = driver.findElement(DEPARTMENT_FILTER_DROPDOWN)
                        .getText()
                        .trim();
            } catch (NoSuchElementException e) {
                currentText = "<element not found>";
            }

            System.out.println(
                    "DEBUG - Department filter did not become 'Quality Assurance' in time. " +
                            "Last seen text: '" + currentText + "'"
            );

            throw timeoutException;
        }
    }

    /**
     * Selects "Istanbul, Turkiye" from the location filter and waits for refreshed cards.
     */
    public void selectLocationIstanbulTurkiye() {
        // Ensure department filter is correctly set before touching location
        waitForDepartmentFilterToBeQualityAssurance();

        // Remember the first card before changing location (used to detect refresh)
        List<WebElement> cardsBefore = driver.findElements(JOB_CARD);
        WebElement firstCardBefore = cardsBefore.isEmpty() ? null : cardsBefore.get(0);

        // Open the location dropdown
        WebElement locationFilter =
                wait.until(ExpectedConditions.elementToBeClickable(LOCATION_FILTER_DROPDOWN));
        locationFilter.click();

        // Wait until options are visible in the dropdown
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("li.select2-results__option")
                )
        );

        // Click "Istanbul, Turkiye"
        WebElement istanbulOption =
                wait.until(ExpectedConditions.elementToBeClickable(ISTANBUL_TURKIYE_OPTION));
        istanbulOption.click();

        // Wait until the old first card is gone (list refreshed)
        if (firstCardBefore != null) {
            try {
                wait.until(ExpectedConditions.stalenessOf(firstCardBefore));
            } catch (TimeoutException ignored) {
                // If the card never becomes stale, rely on the next wait instead
            }
        }

        // Now wait for the updated cards to be visible
        waitUntilJobCardsVisible();
    }

    /**
     * Scrolls to the job list and waits until at least one card is visible.
     */
    public void waitUntilJobCardsVisible() {
        WebElement listSection =
                wait.until(ExpectedConditions.presenceOfElementLocated(JOB_LIST_SECTION));

        // Bring the job list into view so animations can complete
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'start'});", listSection);

        // Ensure wrapper is attached
        wait.until(ExpectedConditions.presenceOfElementLocated(JOB_LIST_WRAPPER));

        // Wait until at least one card is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(JOB_CARD));
    }

    /**
     * Returns all currently visible job cards.
     */
    public List<WebElement> getVisibleJobCards() {
        waitUntilJobCardsVisible();

        return driver.findElements(JOB_CARD);
    }

    /**
     * Helper: checks if a given card matches QA + Istanbul criteria.
     */
    private boolean matchesQaIstanbul(final WebElement card) {
        String departmentText =
                card.findElement(JOB_CARD_DEPARTMENT).getText().trim();

        String locationText =
                card.findElement(JOB_CARD_LOCATION).getText().trim();

        boolean isDepartmentMatching =
                EXPECTED_DEPARTMENT.getDisplayName().equals(departmentText);

        boolean isLocationMatching =
                EXPECTED_LOCATION.getDisplayName().equals(locationText);

        return isDepartmentMatching && isLocationMatching;
    }

    /**
     * Checks if a given job card matches QA + Istanbul criteria.
     * Handles possible stale elements by retrying with fresh card references.
     */
    public boolean isJobCardMatchingQaIstanbulCriteria(final WebElement card) {
        try {
            // First try with the provided card instance
            return matchesQaIstanbul(card);
        } catch (StaleElementReferenceException stale) {
            // The list may have refreshed; retry using fresh card references
            List<WebElement> freshCards = driver.findElements(JOB_CARD);

            for (WebElement freshCard : freshCards) {
                try {
                    if (matchesQaIstanbul(freshCard)) {
                        return true;
                    }
                } catch (StaleElementReferenceException ignored) {
                    // If this card is also stale, skip and continue
                }
            }

            // No stable matching card was found
            return false;
        }
    }

    /**
     * Moves the mouse over the first visible job card.
     */
    public void hoverFirstJobCard() {
        List<WebElement> jobCards = getVisibleJobCards();

        if (jobCards.isEmpty()) {
            throw new IllegalStateException("No job cards are visible to hover.");
        }

        WebElement firstCard = jobCards.get(0);

        actions.moveToElement(firstCard).perform();
    }

    /**
     * Clicks "View Role" on the first visible job card.
     * Includes a small retry to handle rare DOM refresh (stale elements).
     */
    public void clickViewRoleOnFirstJobCard() {
        int attempts = 0;

        while (attempts < 2) {
            try {
                // Always fetch a fresh list of cards
                List<WebElement> jobCards = getVisibleJobCards();

                if (jobCards.isEmpty()) {
                    throw new IllegalStateException("No job cards are visible to click View Role.");
                }

                WebElement firstCard = jobCards.get(0);

                // Hover to reveal the "View Role" button
                actions.moveToElement(firstCard).perform();

                // Find the "View Role" link within the card
                WebElement viewRoleButton = firstCard.findElement(JOB_CARD_VIEW_ROLE_BUTTON);

                // Ensure it is clickable, then click
                wait.until(ExpectedConditions.elementToBeClickable(viewRoleButton));

                viewRoleButton.click();

                return;
            } catch (StaleElementReferenceException stale) {
                attempts++;

                if (attempts >= 2) {
                    // After two failed attempts, propagate the exception
                    throw stale;
                }
                // Loop will retry with fresh elements
            }
        }
    }
}
