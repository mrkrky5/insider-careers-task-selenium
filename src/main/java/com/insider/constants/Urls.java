package com.insider.constants;

/**
 * Central place for all main application URLs.
 */
public final class Urls {

    private Urls() {
        // Utility class – do not instantiate
    }

    /** Root domain for Insider public site. */
    public static final String BASE = "https://useinsider.com";

    /** Careers home page. */
    public static final String CAREERS = BASE + "/careers/";

    /** QA team landing page under Careers. */
    public static final String CAREERS_QA = CAREERS + "quality-assurance/";

    /** QA open positions list with department filter pre-applied. */
    public static final String CAREERS_OPEN_POSITIONS_QA =
            CAREERS + "open-positions/?department=qualityassurance";

    /** Domain fragment used to validate Lever job application URLs. */
    public static final String LEVER_DOMAIN = "lever.co";
}
