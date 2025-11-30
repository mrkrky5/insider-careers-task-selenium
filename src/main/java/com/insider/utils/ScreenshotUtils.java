package com.insider.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Small helper to capture screenshots on demand.
 */
public final class ScreenshotUtils {

    private ScreenshotUtils() {
        // Utility class - do not instantiate
    }

    public static String takeScreenshot(WebDriver driver, String baseName) {
        if (!(driver instanceof TakesScreenshot)) {
            return null;
        }

        String safeName = (baseName == null || baseName.isBlank())
                ? "screenshot"
                : baseName.replaceAll("[^a-zA-Z0-9-_\\.]", "_");

        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = safeName + "_" + timestamp + ".png";

        Path screenshotsDir = Paths.get("screenshots");
        try {
            Files.createDirectories(screenshotsDir);
        } catch (IOException ignored) {
            // If we cannot create the directory, still try to save the file
        }

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path target = screenshotsDir.resolve(fileName);

        try {
            Files.copy(src.toPath(), target);
            return target.toAbsolutePath().toString();
        } catch (IOException e) {
            System.out.println("Could not save screenshot: " + e.getMessage());
            return null;
        }
    }
}
