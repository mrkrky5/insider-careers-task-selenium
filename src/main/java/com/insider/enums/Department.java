package com.insider.enums;

/**
 * Known job departments used in career filters and job cards.
 */
public enum Department {

    QUALITY_ASSURANCE("Quality Assurance"),
    SALES("Sales");

    private final String displayName;

    Department(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the department label as shown in the UI.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if the given text matches this department's display name.
     */
    public boolean matches(final String text) {
        if (text == null) {
            return false;
        }

        return displayName.equals(text.trim());
    }
}
