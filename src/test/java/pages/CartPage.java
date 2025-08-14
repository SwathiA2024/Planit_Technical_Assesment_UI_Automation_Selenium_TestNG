package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.LogUtil;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(id = "nav-cart")
    private WebElement cartLink;

    @FindBy(css = "table.cart-items tbody tr.cart-item")
    private List<WebElement> cartRows;

    @FindBy(css = "tfoot strong.total")
    private WebElement totalPriceElement;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void goToCartPage() {
        cartLink.click();
        String cartPageUrlContainsCart = "/cart";
        isUserOnPage(driver, cartPageUrlContainsCart);
        LogUtil.logStep("Navigate to Cart Page");
    }

    public double getProductPrice(String productName) {
        for (WebElement row : cartRows) {
            String name = row.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
            if (name.equalsIgnoreCase(productName)) {
                String priceText = row.findElement(By.cssSelector("td:nth-child(2)")).getText().replace("$", "");
                return Double.parseDouble(priceText);
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public void verifyProductPriceMatchesShopPageProductPrice(String productName, double shopPrice) {
        double cartPrice = getProductPrice(productName);
        LogUtil.logStepWithScreenshot(driver, productName + " - Shop Price: " + shopPrice + ", Cart Price: " + cartPrice);
        Assert.assertEquals(cartPrice, shopPrice, 0.01, "Price mismatch for " + productName);
    }

    public int getQuantity(String productName) {
        for (WebElement row : cartRows) {
            String name = row.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
            if (name.equalsIgnoreCase(productName)) {
                String qty = row.findElement(By.cssSelector("input[name='quantity']")).getAttribute("value");
                return Integer.parseInt(qty);
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public double getSubtotal(String productName) {
        for (WebElement row : cartRows) {
            String name = row.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
            if (name.equalsIgnoreCase(productName)) {
                String subtotalText = row.findElement(By.cssSelector("td:nth-child(4)")).getText().replace("$", "");
                return Double.parseDouble(subtotalText);
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public double getTotalProductsPrice() {
        String totalText = totalPriceElement.getText().replace("Total: ", "");
        return Double.parseDouble(totalText);
    }

    public void verifyProductSubtotalPrice(String productName) {
        double price = getProductPrice(productName);
        int quantity = getQuantity(productName);
        double expectedSubtotal = price * quantity;
        double actualSubtotal = getSubtotal(productName);
        LogUtil.logStepWithScreenshot(driver, productName + " - Expected Subtotal: " + expectedSubtotal + ", Actual Subtotal: " + actualSubtotal);
        Assert.assertEquals(actualSubtotal, expectedSubtotal, 0.01);
    }

    public void verifyTotalProductsPrice() {
        double expectedTotalProductsPrice = 0;
        for (WebElement row : cartRows) {
            String subtotalText = row.findElement(By.cssSelector("td:nth-child(4)")).getText().replace("$", "");
            expectedTotalProductsPrice += Double.parseDouble(subtotalText);
        }
        double actualTotalProductsPrice = getTotalProductsPrice();
        LogUtil.logStepWithScreenshot(driver,"Expected Total: " + expectedTotalProductsPrice + ", Actual Total: " + actualTotalProductsPrice);
        Assert.assertEquals(actualTotalProductsPrice, expectedTotalProductsPrice, 0.01);
    }
}