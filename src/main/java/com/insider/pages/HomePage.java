package com.insider.pages;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object for the public Insider home page.
 * Handles high-level navigation such as opening the Careers section.
 */
public class HomePage extends BasePage {

    /** Top navigation "Company" menu entry. */
    private static final By COMPANY_MENU = By.xpath(
            "//nav//a[normalize-space()='Company']"
    );

    /** "Careers" link under the Company menu. */
    private static final By CAREERS_LINK_IN_MENU = By.xpath(
            "//nav//a[normalize-space()='Careers' and contains(@href, '/careers')]"
    );

    public HomePage(final WebDriver driver) {
        super(driver);
    }

    /**
     * Opens the home page and accepts the cookie banner if present.
     */
    public HomePage openHomePage(final String baseUrl) {
        driver.get(baseUrl);
        acceptCookiesIfPresent();

        return this;
    }

    /**
     * Performs a simple sanity check that the home page is loaded.
     * Uses the page title as a heuristic.
     */
    public boolean isHomePageLoaded() {
        String title = driver.getTitle();
        String titleLower = title.toLowerCase(Locale.ROOT);

        return titleLower.contains("insider");
    }

    /**
     * Opens the Careers page via the Company menu in the top navigation.
     */
    public void openCareersPageFromCompanyMenu() {
        clickElement(COMPANY_MENU);
        clickElement(CAREERS_LINK_IN_MENU);
    }
}
