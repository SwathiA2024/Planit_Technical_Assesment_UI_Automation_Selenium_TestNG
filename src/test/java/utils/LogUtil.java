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
}