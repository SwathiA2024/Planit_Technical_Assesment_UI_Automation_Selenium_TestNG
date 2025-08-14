package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;

public class LogUtil {
    private static ExtentTest test;

    public static void setTest(ExtentTest extentTest) {
        test = extentTest;
    }

    public static void logStep(String message) {
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    public static void logStepWithScreenshot(WebDriver driver, String message) {
        System.out.println(message);
        if (test != null) {
            try {
                String uniqueMessage = message + "_" + System.currentTimeMillis();
                String screenshotPath = ScreenshotUtils.takeScreenshot(driver, uniqueMessage.replace(" ", "_")
                        .replace(",", "_")
                        .replace(":", "_"));
                test.log(Status.PASS, message,
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
                );
            } catch (Exception e) {
                test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
            }
        }
    }

    public static void failWithScreenshot(WebDriver driver, String message, Throwable t) {
        if (test == null) return;
        try {
            String screenshotPath = ScreenshotUtils.takeScreenshot(
                    driver, ("FAIL_" + message).replaceAll("[\\s,:]", "_") + "_" + System.currentTimeMillis());
            test.fail(t != null ? t : new AssertionError(message),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            test.fail(message + " (screenshot unavailable: " + e.getMessage() + ")");
        }
    }

    public static void pass(String message) {
        if (test != null) test.pass(message);
    }

    public static void skip(String message) {
        if (test != null) test.skip(message);
    }
}