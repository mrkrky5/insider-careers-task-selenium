package com.insider.utils;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.WebDriver;

/**
 * Window-related helper methods, e.g. waiting for a new tab or window.
 */
public final class WindowUtils {

    private WindowUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Waits until a new window handle appears and returns it.
     *
     * @param driver          the WebDriver instance
     * @param existingHandles the set of handles that existed before the action
     * @param timeout         maximum time to wait for a new window
     * @param pollInterval    how often to check for new handles
     * @return the new window handle, or {@code null} if none appears in time
     */
    public static String waitForNewWindowHandle(
            final WebDriver driver,
            final Set<String> existingHandles,
            final Duration timeout,
            final Duration pollInterval) {

        long endTime = System.currentTimeMillis() + timeout.toMillis();

        while (System.currentTimeMillis() < endTime) {
            Set<String> currentHandles = driver.getWindowHandles();

            // If we see more handles than before, return the new one
            if (currentHandles.size() > existingHandles.size()) {
                for (String handle : currentHandles) {
                    if (!existingHandles.contains(handle)) {
                        return handle;
                    }
                }
            }

            try {
                Thread.sleep(pollInterval.toMillis());
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        // No new window appeared within the timeout
        return null;
    }
}
