package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    @DataProvider(name = "allProducts")
    public static Object[][] allProducts() {
        return new Object[][]{
                {
                        new String[]{"Stuffed Frog", "Fluffy Bunny", "Valentine Bear"},
                        new int[]{2, 5, 3}
                }
        };
    }

    @Test(description = "Verify cart subtotal and total calculations", priority = 3, dataProvider = "allProducts")
    public void testCase3_verifyCartPriceCalculations(String[] productNames, int[] quantities) {
        homePage.clickOnStartShoppingButton();
        for (int i = 0; i < productNames.length; i++) {
            String productName = productNames[i];
            int quantity = quantities[i];
            double price = shopPage.getProductPrice(productName);
            shopPage.addProductQuantity(productName, quantity);
            cartPage.goToCartPage();
            cartPage.verifyProductPriceMatchesShopPageProductPrice(productName, price);
            shopPage.navigateToShopPage();
        }
        cartPage.goToCartPage();
        for (String productName : productNames) {
            cartPage.verifyProductSubtotalPrice(productName);
        }
        cartPage.verifyTotalProductsPrice();
    }
}