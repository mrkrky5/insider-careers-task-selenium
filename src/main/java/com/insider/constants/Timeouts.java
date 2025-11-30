package com.insider.constants;

import java.time.Duration;

/**
 * Central place for all wait times, expressed with intent.
 * Timeouts can be scaled with the system property `timeouts.multiplier`.
 * Example: -Dtimeouts.multiplier=2.0  => all waits x2
 */
public final class Timeouts {

    private Timeouts() {
        // utility class
    }

    // Read once at startup. Default = 1.0 (no change)
    private static final double MULTIPLIER = Double.parseDouble(
            System.getProperty("timeouts.multiplier", "1.0")
    );

    private static Duration seconds(long baseSeconds) {
        long scaled = (long) Math.ceil(baseSeconds * MULTIPLIER);
        return Duration.ofSeconds(scaled);
    }

    /** Standard wait for visible / clickable elements (buttons, inputs, etc.) */
    public static final Duration ELEMENT = seconds(15);

    /** Wait for full page navigation, URL changes, big layout changes. */
    public static final Duration PAGE = seconds(20);

    /**
     * Wait for slow UI updates like filters, animations,
     * Ajax-based lists (e.g. Open Positions filters).
     */
    public static final Duration UI_REFRESH = seconds(30); // biraz arttırdım

    /** Optional: very short checks (e.g. absence of element) */
    public static final Duration QUICK_CHECK = seconds(5);

    /** Dropdown açılma / animasyon gibi kısa ama görsel hareketler. */
    public static final Duration DROPDOWN_ANIMATION = seconds(10);
}
