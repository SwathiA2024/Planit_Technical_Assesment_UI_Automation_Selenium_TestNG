package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void waitForWebElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForPageLoadComplete(int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static void isUserOnPage(WebDriver driver, String expectedUrlPart, WebElement uniqueElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));
            wait.until(ExpectedConditions.visibilityOf(uniqueElement));
            if (!driver.getCurrentUrl().contains(expectedUrlPart) || !uniqueElement.isDisplayed()) {
                throw new RuntimeException("Not on expected page");
            }
        } catch (Exception e) {
            throw new RuntimeException("Page verification failed: " + e.getMessage());
        }
    }

    public static void isUserOnPage(WebDriver driver, String expectedUrlPart) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));
            if (!driver.getCurrentUrl().contains(expectedUrlPart)) {
                throw new RuntimeException("Not on expected page");
            }
        } catch (Exception e) {
            throw new RuntimeException("Page verification failed: " + e.getMessage());
        }
    }
}