package main;

import com.google.common.io.Files;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import helpers.ReportHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@CucumberOptions(strict = true, monochrome = true, features = "src/test/resources/features", glue = "stepDefinitions", plugin = {"pretty","json:target/cucumber.json"}, tags = { "@Regression,@JunitScenario,@TestNGScenario" })

public class CucumberRunner extends AbstractTestNGCucumberTests {

    public static Properties config = null;
    public static WebDriver driver = null;


    public void LoadConfigProperty() throws IOException {
        config = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config/config.properties");
        config.load(fileInputStream);
    }


    public void configureDriverPath() throws IOException {
        if(System.getProperty("os.name").startsWith("Linux")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/geckodriver";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/chromedriver";
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
        if(System.getProperty("os.name").startsWith("Mac")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/geckodriver";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/chromedriver";
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
        if(System.getProperty("os.name").startsWith("Windows")) {
            String firefoxDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//geckodriver.exe";
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            String chromeDriverPath = System.getProperty("user.dir") + "//src//test//resources//drivers//windows//chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }
    }


    public void openBrowser() throws Exception {
        // load the config optiona
        LoadConfigProperty();

        // configure the driver path
        configureDriverPath();
        if(config.getProperty("browserType").equals("firefox")) {
            driver = new FirefoxDriver();
        }else if (config.getProperty("browserType").equals("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
           // chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.setExperimentalOption("useAutomationExtension", false);
            driver = new ChromeDriver(chromeOptions);
        }
    }


    public void maximizedWindow() {
        driver.manage().window().maximize();
    }

    public void implicitWait(int time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public void explicitWait(WebElement webElement) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 3000);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void pageLoad(int time) {
        driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void setEnv() throws Exception {
        LoadConfigProperty();
        String baseUrl = config.getProperty("siteUrl");
        driver.get(baseUrl);
    }

    public static String currentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String cal = dateFormat.format(calendar.getTime());
        return cal;
    }

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        openBrowser();
        maximizedWindow();
        implicitWait(30);
        deleteAllCookies();
        setEnv();
        implicitWait(30);
    }

    @AfterClass(alwaysRun = true)
    public void takeScreenshot() throws Exception {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File targetFile = new File(System.getProperty("user.dir") + "//screenshots/screenshopt.png");
        targetFile.getParentFile().mkdir();
        targetFile.createNewFile();
        Files.copy(scrFile, targetFile);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult iTestResult) throws Exception {
        if(iTestResult.isSuccess()) {
            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy'T'HH:ss");
            String failureImageFileName = iTestResult.getMethod().getMethodName() + simpleDateFormat.format(new GregorianCalendar().getTime()) + ".png";


            File failureImageFile = new File(System.getProperty("user.dir") + "//screenshot//" + failureImageFileName);
            failureImageFile.getParentFile().mkdir();
            failureImageFile.createNewFile();
            Files.copy(imageFile, failureImageFile);
        }
    }


    @AfterSuite(alwaysRun = true)
    public void generateHTMLReports() {
        ReportHelper.generateCucumberReport();
    }

    @AfterSuite(alwaysRun = true)
    public void quit() throws Exception, InterruptedException {
        driver.quit();
    }

}
