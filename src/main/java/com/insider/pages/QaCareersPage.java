package com.insider.pages;

import static com.insider.constants.Urls.CAREERS_QA;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object for the Quality Assurance careers landing page.
 */
public class QaCareersPage extends BasePage {

    /** Main page heading for the QA careers page. */
    private static final By QA_PAGE_HEADING = By.xpath(
            "//h1[contains(normalize-space(), 'Quality Assurance')]"
    );

    /** "See all QA jobs" call-to-action button. */
    private static final By SEE_ALL_QA_JOBS_BUTTON = By.xpath(
            "//a[contains(normalize-space(), 'See all QA jobs')]"
    );

    public QaCareersPage(final WebDriver driver) {
        super(driver);
    }

    /**
     * Opens the QA careers page and accepts the cookie banner if present.
     */
    public QaCareersPage open() {
        driver.get(CAREERS_QA);
        acceptCookiesIfPresent();

        return this;
    }

    /**
     * Basic check that the QA careers page is loaded.
     * Uses heading visibility or URL as a fallback.
     */
    public boolean isQaCareersPageLoaded() {
        return isElementDisplayed(QA_PAGE_HEADING)
                || getCurrentUrl().contains("/careers/quality-assurance");
    }

    /**
     * Verifies that the "See all QA jobs" button is visible.
     */
    public boolean isSeeAllQaJobsButtonVisible() {
        return isElementDisplayed(SEE_ALL_QA_JOBS_BUTTON);
    }

    /**
     * Clicks the "See all QA jobs" button.
     */
    public void clickSeeAllQaJobs() {
        clickElement(SEE_ALL_QA_JOBS_BUTTON);
    }
}
