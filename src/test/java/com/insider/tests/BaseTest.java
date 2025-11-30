package com.insider.tests;

import static com.insider.constants.Timeouts.PAGE;
import static com.insider.constants.Urls.BASE;

import java.util.HashMap;
import java.util.Map;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base test class that sets up and tears down WebDriver for each test run.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final String BASE_URL = BASE;

    @BeforeMethod
    public void setUp() {
        // Setup ChromeDriver with WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Configure Chrome to disable notifications
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Explicit wait for page-level operations (navigation, URL changes, etc.)
        wait = new WebDriverWait(driver, PAGE);
    }
    public WebDriver getDriver() {
        return driver;
    }
    

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // Ensure browser is closed after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
