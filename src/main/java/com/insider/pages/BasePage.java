package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.insider.constants.Timeouts.ELEMENT;

/**
 * Base class for all page objects.
 * Provides common waits, scrolling helpers and basic interactions.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final int DEFAULT_SCROLL_PIXELS = 300;
    private static final By COOKIE_ACCEPT_ALL_BUTTON = By.id("wt-cli-accept-all-btn");

    protected BasePage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, ELEMENT);
    }

    /**
     * Waits until the element located by the given locator is visible.
     */
    protected WebElement waitForVisibility(final By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until the element located by the given locator is clickable.
     */
    protected WebElement waitForClickable(final By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Clicks the element when it becomes clickable.
     */
    protected void clickElement(final By locator) {
        WebElement element = waitForClickable(locator);

        element.click();
    }

    /**
     * Clears the input and types the given text.
     */
    protected void setInputText(final By locator, final String text) {
        WebElement element = waitForVisibility(locator);

        element.clear();
        element.sendKeys(text);
    }

    /**
     * Returns the visible text of the element.
     */
    protected String getElementText(final By locator) {
        WebElement element = waitForVisibility(locator);

        return element.getText();
    }

    /**
     * Returns true if the element becomes visible within the default timeout.
     */
    protected boolean isElementDisplayed(final By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    /**
     * Scrolls the page until the element is in the center of the viewport.
     */
    protected void scrollToElement(final By locator) {
        WebElement element = waitForVisibility(locator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }

    /**
     * Scrolls the page down by a default offset.
     */
    protected void scrollByDefaultAmount() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollBy(0, arguments[0]);",
                DEFAULT_SCROLL_PIXELS
        );
    }

    /**
     * Returns the current browser URL.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Tries to accept the global cookie banner if it is present.
     * If the banner is not visible, the method exits silently.
     */
    public void acceptCookiesIfPresent() {
        try {
            WebElement button = wait.until(
                    ExpectedConditions.elementToBeClickable(COOKIE_ACCEPT_ALL_BUTTON)
            );

            button.click();
        } catch (TimeoutException | NoSuchElementException ignored) {
            // Cookie banner not shown; continue without failing the test
        }
    }
}
