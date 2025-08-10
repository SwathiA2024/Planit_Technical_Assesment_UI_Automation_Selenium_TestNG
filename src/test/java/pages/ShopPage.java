package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.LogUtil;

public class ShopPage extends BasePage {

    @FindBy(id = "nav-shop")
    private WebElement shopLink;

    public ShopPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void goToShopPage() {
        waitForWebElementVisibility(shopLink);
        shopLink.click();
        LogUtil.logStep("Navigate to Shop page");
    }

    public WebElement getProductBuyButton(String productName) {
        String buyButtonXpathForProducts = "//li[contains(@id,'product') and .//h4[text()='%s']]//a[text()='Buy']";
        String xpath = String.format(buyButtonXpathForProducts, productName);
        return driver.findElement(By.xpath(xpath));
    }

    public void addProductQuantity(String productName, int quantity) {
        waitForPageLoadComplete(10);
        WebElement buyBtnElement = getProductBuyButton(productName);
        for (int i = 0; i < quantity; i++) {
            buyBtnElement.click();
        }
        LogUtil.logStepWithScreenshot(driver, "Added product:" + productName + " of quantity = " + quantity + " to cart");
    }

    public double getProductPrice(String productName) {
        String xpath = String.format("//h4[text()='%s']/following-sibling::p", productName);
        String priceText = driver.findElement(By.xpath(xpath)).getText().split(" ")[0].replace("$", "");
        return Double.parseDouble(priceText);
    }
}