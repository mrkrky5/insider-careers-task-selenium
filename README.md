# Insider Careers – Selenium Test Suite (Java + TestNG)

UI test automation suite for the Insider Careers pages, implemented with Java, Selenium WebDriver and TestNG using the Page Object Model (POM).

The suite covers navigation, core content checks, QA open positions filters and the integration with the external Lever application.

## Tech stack

Java 17 (11+ compatible), TestNG, Selenium WebDriver, Chrome (managed via WebDriverManager) and Maven as build tool.

## Project Structure

```text
src
└── main
    └── java
        ├── com.insider.constants
        │   ├── Timeouts.java      // Named timeouts with intent (ELEMENT, PAGE, UI_REFRESH, etc.)
        │   └── Urls.java          // Central source of application URLs
        │
        ├── com.insider.enums
        │   ├── Department.java    // Department names (e.g. QUALITY_ASSURANCE)
        │   └── Location.java      // Location names (e.g. ISTANBUL_TURKIYE)
        │
        ├── com.insider.pages
        │   ├── BasePage.java      // Shared WebDriver + waits + helpers + cookie handling
        │   ├── HomePage.java      // Home page navigation (Company → Careers)
        │   ├── CareersPage.java   // Careers page sections (Locations, Teams, Life at Insider)
        │   ├── QaCareersPage.java // QA careers landing page ("See all QA jobs")
        │   └── OpenPositionsPage.java
        │                         // Open positions filters + QA/Istanbul cards + Lever link
        │
        └── com.insider.utils
            └── WindowUtils.java   // Helper for waiting new browser window/tab (Lever)
src
└── test
    └── java
        └── com.insider.tests
            ├── BaseTest.java              // WebDriver/WebDriverWait setup & teardown
            ├── HomePageSmokeTest.java     // Basic home page smoke test
            ├── CareersNavigationTest.java // Navigation: Home → Company → Careers
            ├── CareersPageTest.java       // Key sections on Careers page
            ├── QaCareersPageTest.java     // QA Careers page and "See all QA jobs" visibility
            └── OpenPositionsPageTest.java // Filters + card checks + Lever tab behaviour
```
## File responsibilities

Timeouts.java  
Holds shared `Duration` constants for different wait types such as element waits, full page loads and slower UI refreshes.

Urls.java  
Defines the base URL and all entry-point URLs used in the tests, including QA careers and QA open positions.

TestGroups.java  
Provides central string constants for TestNG groups so group names are consistent across the suite.

Department.java  
Enum for departments (for now Quality Assurance and Sales) with a helper to match display text safely.

Location.java  
Enum for locations (currently Istanbul, Turkiye) with the same text-matching helper.

BasePage.java  
Abstract base class for all pages. Wraps WebDriver and WebDriverWait, exposes common helpers (waits, scrolling, text access) and handles the global cookie banner if present.

HomePage.java  
Represents the Insider home page. Knows how to open the base URL, accept cookies and navigate to the Careers page via the Company menu.

CareersPage.java  
Models the generic Careers page and provides high-level checks for Locations, Teams and Life at Insider sections.

QaCareersPage.java  
Represents the QA-specific careers landing page. Opens the QA URL, verifies that the page is loaded and that the “See all QA jobs” button is visible and clickable.

OpenPositionsPage.java  
Contains locators and logic for the open-positions view: clicking “See all QA jobs”, waiting for the department filter to auto-fill, selecting Istanbul in the location filter, reading job cards, verifying QA + Istanbul criteria and clicking the Lever “View Role” link with retry logic for DOM refreshes.

WindowUtils.java  
Small utility class that polls for a new window or tab and returns its handle, used when validating that Lever opens in a separate tab.

BaseTest.java  
Abstract TestNG base class that sets up and tears down ChromeDriver with sensible options and initialises the shared WebDriverWait.

HomePageSmokeTest.java  
Fast smoke test that opens the base URL and checks the page title to confirm the site is up.

CareersNavigationTest.java  
Uses the real navigation path from Home → Company → Careers and asserts that the browser ends up on a careers URL.

CareersPageTest.java  
Navigates to the Careers page and verifies that the key sections (Locations, Teams, Life at Insider) are present and visible to the user.

QaCareersPageTest.java  
Directly opens the QA careers page and checks both that the page is loaded and that the “See all QA jobs” call-to-action is displayed.

OpenPositionsPageTest.java  
End-to-end flow starting from the QA careers page: clicks “See all QA jobs”, verifies the QA open positions URL, applies department and location filters, validates every job card and confirms that clicking “View Role” opens Lever in a new tab.

## Notes

This project was prepared as a focused UI automation suite for the Insider Careers pages.  
The structure is intentionally small but extendable, so new pages and scenarios can easily be added on top of the existing POM and utilities.
