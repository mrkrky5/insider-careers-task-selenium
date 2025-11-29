package com.insider.enums;

/**
 * Known job locations used in career filters and job cards.
 */
public enum Location {

    ISTANBUL_TURKIYE("Istanbul, Turkiye");

    private final String displayName;

    Location(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the location label as shown in the UI.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if the given text matches this location's display name.
     */
    public boolean matches(final String text) {
        if (text == null) {
            return false;
        }

        return displayName.equals(text.trim());
    }
}
