package com.insider.constants;

import java.time.Duration;

/**
 * Central place for all explicit wait durations.
 */
public final class Timeouts {

    private Timeouts() {
        // Utility class: not meant to be instantiated
    }

    /** Default wait for visible / clickable UI elements (buttons, inputs, etc.). */
    public static final Duration ELEMENT = Duration.ofSeconds(15);

    /** Wait for full page loads, URL changes and large layout updates. */
    public static final Duration PAGE = Duration.ofSeconds(20);

    /**
     * Wait for slower UI updates such as filters, animations
     * or Ajax-based list refreshes (e.g. Open Positions filters).
     */
    public static final Duration UI_REFRESH = Duration.ofSeconds(25);

    /** Short checks, e.g. verifying that an element is not present or is quickly rendered. */
    public static final Duration QUICK_CHECK = Duration.ofSeconds(5);

    /** Extra buffer for dropdown open/close animations before interacting with options. */
    public static final Duration DROPDOWN_ANIMATION = Duration.ofSeconds(15);
}
