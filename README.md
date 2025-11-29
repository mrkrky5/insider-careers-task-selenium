# Insider Careers – Selenium Test Suite (Java + TestNG)

UI test automation suite for the **Insider Careers** pages, implemented with **Java**, **Selenium WebDriver**, and **TestNG**, following the Page Object Model (POM) pattern.

The suite covers navigation, content checks, filter behaviour and integration with the external **Lever** application page.

---

## Tech Stack

- **Language:** Java 17 (or 11+)
- **Test Framework:** TestNG
- **Browser Automation:** Selenium WebDriver
- **Browser:** Google Chrome (managed with WebDriverManager)
- **Build Tool:** Maven
- **Design Pattern:** Page Object Model (POM)
- **Locator Style:** Optimized **XPath / CSS** selectors for buttons, dropdowns and interactive elements (as requested by Insider).

---

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
