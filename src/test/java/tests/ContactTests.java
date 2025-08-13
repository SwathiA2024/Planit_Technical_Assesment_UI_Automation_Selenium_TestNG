package tests;

import base.BaseTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ContactTests extends BaseTest {

    @Test(description = "Verify contact page shows/removes error messages correctly", priority = 1)
    @Parameters({"forename", "email", "message"})
    public void testCase1_verifyContactPageErrorValidation(String forename, String email, String message) {
        contactPage.navigateToContactPage();
        contactPage.clickSubmitButton();
        contactPage.verifyErrorMessagesDisplayed();
        contactPage.fillMandatoryFields(forename, email, message);
        contactPage.verifyNoErrorMessagesDisplayed();
    }

    @Test(description = "Verify contact form submits successfully with valid data", priority =2, invocationCount = 5)
    @Parameters({"forename", "email", "message"})
    public void testCase2_verifyContactFormSubmission(String forename, String email, String message) {
        contactPage.navigateToContactPage();
        contactPage.fillMandatoryFields(forename, email, message);
        contactPage.clickSubmitButton();
        contactPage.waitForSendingFeedbackPopupToDisappear();
        contactPage.verifySuccessMessage();
    }
}