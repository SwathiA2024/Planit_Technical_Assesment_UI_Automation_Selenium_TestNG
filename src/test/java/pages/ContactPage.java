package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import utils.LogUtil;

import java.time.Duration;
import java.util.List;

public class ContactPage extends BasePage {

    @FindBy(xpath = "//div[@class='form-actions']//a[@class='btn-contact btn btn-primary']")
    private WebElement submitButton;

    @FindBy(id = "forename")
    private WebElement forenameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "message")
    private WebElement messageField;

    @FindBy(xpath = "//div[@class='controls']//span[contains(@id,'err')]")
    private List<WebElement> errorMessages; // All error messages container

    @FindBy(css = ".alert-success")
    private WebElement successMessage; // Success message container

    @FindBy(css = ".popup.modal.ng-scope")
    private WebElement sendingFeedbackPopup;

    @FindBy(linkText = "Contact")
    private WebElement contactLink;

    public ContactPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToContactPage() {
        contactLink.click();
        String contactPageUrl = "/contact";
        isUserOnPage(driver, contactPageUrl, submitButton);
        waitForPageLoadComplete(10);
        LogUtil.logStepWithScreenshot(driver, "Navigate to Contact Page");
    }

    public void clickSubmitButton() {
        waitForWebElementVisibility(submitButton);
        submitButton.click();
        LogUtil.logStepWithScreenshot(driver, "Click Submit button");
    }

    public void fillMandatoryFields(String forename, String email, String message) {
        forenameField.sendKeys(forename);
        emailField.sendKeys(email);
        messageField.sendKeys(message);
        LogUtil.logStepWithScreenshot(driver, "Populate mandatory fields");
    }

    public void verifyErrorMessagesDisplayed() {
        if (!errorMessages.isEmpty()) {
            for (WebElement error : errorMessages) {
                LogUtil.logStep("Error: " + error.getText() + " is displayed");
            }
        }
        Assert.assertFalse(errorMessages.isEmpty(), "Error messages are not displayed!");
        LogUtil.logStepWithScreenshot(driver, "Error messages are displayed");
    }

    public void verifyNoErrorMessagesDisplayed() {
        Assert.assertTrue(errorMessages.isEmpty(), "Error messages are still displayed!");
        LogUtil.logStepWithScreenshot(driver, "Verify error messages are gone");
    }

    public void waitForSendingFeedbackPopupToDisappear() {
        LogUtil.logStepWithScreenshot(driver, "FeedBack Popup is displayed with Loading bar");
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        wait.until(driver -> {
            try {
                WebElement popup = sendingFeedbackPopup;
                return !popup.isDisplayed() || "true".equals(popup.getAttribute("aria-hidden"));
            } catch (NoSuchElementException e) {
                return true;
            }
        });
    }

    public void verifySuccessMessage() {
        Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed!");
        LogUtil.logStepWithScreenshot(driver, "Verify success message is displayed");
    }
}