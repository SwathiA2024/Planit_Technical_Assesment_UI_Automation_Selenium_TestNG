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
import pages.*;
import utils.ConfigReaderUtil;
import utils.ExtentManager;
import utils.LogUtil;
import utils.ScreenshotUtils;

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
    public void setUp(String browser, Method method) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--force-device-scale-factor=1");
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(ConfigReaderUtil.getBaseUrl());
        initializePages();
        test = extent.createTest(method.getName());
        ScreenshotUtils.setDriver(driver);
        LogUtil.setTest(test);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            driver.quit();
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