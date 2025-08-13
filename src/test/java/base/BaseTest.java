package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.CartPage;
import pages.ContactPage;
import pages.HomePage;
import pages.ShopPage;
import utils.ConfigReaderUtil;
import utils.ExtentManager;
import utils.LogUtil;


import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected ContactPage contactPage;
    protected CartPage cartPage;
    protected ShopPage shopPage;
    public static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        extent = ExtentManager.getInstance();
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional String browser, Method method) {
        String chosen = (browser != null && !browser.isBlank())
                ? browser
                : System.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        if (chosen.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments(
                        "--headless=new",
                        "--window-size=1920,1080",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu"
                );
            } else {
                options.addArguments("--start-maximized", "--force-device-scale-factor=1");
            }
            driver = new ChromeDriver(options);
            if (!headless) driver.manage().window().maximize();

        } else if (chosen.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (headless) options.addArguments("--headless");
            driver = new FirefoxDriver(options);
            if (headless) {
                driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
            } else {
                driver.manage().window().maximize();
            }

        } else {
            throw new IllegalArgumentException("Unsupported browser: " + chosen);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(ConfigReaderUtil.getBaseUrl());
        initializePages();
        test = extent.createTest(method.getName());
        LogUtil.setTest(test);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            switch (result.getStatus()) {
                case ITestResult.FAILURE -> LogUtil.failWithScreenshot(
                        driver, "Test failed: " + result.getName(), result.getThrowable());
                case ITestResult.SUCCESS -> LogUtil.pass("Test Passed: " + result.getName());
                case ITestResult.SKIP -> LogUtil.skip("Test Skipped: " + result.getName());
            }
        } finally {
            if (driver != null) driver.quit();
        }
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }

    private void initializePages() {
        homePage = PageFactory.initElements(driver, HomePage.class);
        contactPage = PageFactory.initElements(driver, ContactPage.class);
        cartPage = PageFactory.initElements(driver, CartPage.class);
        shopPage = PageFactory.initElements(driver, ShopPage.class);
    }
}