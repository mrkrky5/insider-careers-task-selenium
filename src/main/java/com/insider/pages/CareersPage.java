package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object for the main Careers page.
 * Verifies key content blocks like Locations, Teams and Life at Insider.
 */
public class CareersPage extends BasePage {

    /** Generic careers page header. */
    private static final By CAREERS_PAGE_HEADER = By.xpath(
            "//h1[contains(normalize-space(), 'Careers')]"
    );

    /** Section that highlights Insider office locations. */
    private static final By LOCATIONS_SECTION_HEADER = By.xpath(
            "//section[.//h2[contains(normalize-space(), 'Locations')] " +
                    "or .//h3[contains(normalize-space(), 'Locations')]]"
    );

    /** Section that lists teams or roles (wording may vary). */
    private static final By TEAMS_SECTION_HEADER = By.xpath(
            "//section[.//h2[contains(normalize-space(), 'Teams')] " +
                    "or .//h3[contains(normalize-space(), 'Teams')] " +
                    "or .//h2[contains(normalize-space(), 'Find your calling')] " +
                    "or .//h3[contains(normalize-space(), 'Find your calling')]]"
    );

    /** Section that describes Life at Insider. */
    private static final By LIFE_AT_INSIDER_SECTION_HEADER = By.xpath(
            "//section[.//h2[contains(normalize-space(), 'Life at Insider')] " +
                    "or .//h3[contains(normalize-space(), 'Life at Insider')]]"
    );

    public CareersPage(final WebDriver driver) {
        super(driver);
    }

    /**
     * Basic check that we are on the Careers page.
     * Uses header visibility or URL as a fallback.
     */
    public boolean isCareersPageLoaded() {
        return isElementDisplayed(CAREERS_PAGE_HEADER)
                || getCurrentUrl().contains("/careers");
    }

    /**
     * Scrolls to and verifies the Locations section.
     */
    public boolean isLocationsSectionVisible() {
        scrollToElement(LOCATIONS_SECTION_HEADER);

        return isElementDisplayed(LOCATIONS_SECTION_HEADER);
    }

    /**
     * Scrolls to and verifies the Teams / roles section.
     */
    public boolean isTeamsSectionVisible() {
        scrollToElement(TEAMS_SECTION_HEADER);

        return isElementDisplayed(TEAMS_SECTION_HEADER);
    }

    /**
     * Scrolls to and verifies the “Life at Insider” section.
     */
    public boolean isLifeAtInsiderSectionVisible() {
        scrollToElement(LIFE_AT_INSIDER_SECTION_HEADER);

        return isElementDisplayed(LIFE_AT_INSIDER_SECTION_HEADER);
    }
}
