package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.LogUtil;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[@class='btn btn-success btn-large']")
    private WebElement startShoppingButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickOnStartShoppingButton() {
        LogUtil.logStep("Click on start shopping button");
        waitForWebElementVisibility(startShoppingButton);
        startShoppingButton.click();
    }
}