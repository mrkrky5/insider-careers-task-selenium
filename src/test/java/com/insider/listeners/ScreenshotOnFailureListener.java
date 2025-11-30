package com.insider.listeners;

import com.insider.tests.BaseTest;
import com.insider.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Captures a screenshot whenever a test fails.
 */
public class ScreenshotOnFailureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest)) {
            return;
        }

        BaseTest baseTest = (BaseTest) instance;
        WebDriver driver = baseTest.getDriver();
        if (driver == null) {
            return;
        }

        String methodName = result.getMethod().getMethodName();
        String path = ScreenshotUtils.takeScreenshot(driver, methodName);

        if (path != null) {
            System.out.println("[SCREENSHOT] Saved to: " + path);
        } else {
            System.out.println("[SCREENSHOT] Could not capture screenshot for: " + methodName);
        }
    }

    @Override public void onTestStart(ITestResult result) { }
    @Override public void onTestSuccess(ITestResult result) { }
    @Override public void onTestSkipped(ITestResult result) { }
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }
    @Override public void onTestFailedWithTimeout(ITestResult result) { }
    @Override public void onStart(ITestContext context) { }
    @Override public void onFinish(ITestContext context) { }
}
