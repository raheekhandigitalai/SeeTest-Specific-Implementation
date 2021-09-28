package selenium.performance;

import helpers.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class PerformanceTransaction {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This approach is applicable for ANY browser (Safari / IE / Chromium Edge, Chrome, Firefox).
     *
     * In the SeeTestCloud, we have the ability to capture Performance Metrics for our Selenium Tests.
     * In the report, we capture data such as Duration, Speed Index and Network Traffic.
     *
     * Recording Network Traffic in form of .har file required an additional capability to be added:
     *
     *      desiredCapabilities.setCapability("useNV", true); // Default - false
     *
     * HAR files are produced only after the end of the test, you can view/download them after
     * the test is finished and not during it's execution(for example in java written test the
     * HAR file are produced after driver.quit()).
     *
     * https://docs.experitest.com/display/TE/Grid+-+Performance+Transaction+Commands
     *
     * https://docs.experitest.com/display/TE/Transaction+View
     *
     */

    protected RemoteWebDriver driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("browserName", "chrome");

        desiredCapabilities.setCapability("useNV", true);

        driver = new RemoteWebDriver(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void performance_transaction_testing() {
        // Start of a Transaction
        driver.executeScript("seetest:client.startPerformanceTransaction(\"1.2\")");

        driver.navigate().to("https://github.com/login");
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("login_field")));

        // End  of a Transaction
        Object navigationTransaction = driver.executeScript("seetest:client.endPerformanceTransaction(\"GitHub_Navigate_To_Login\")");
        System.out.println(navigationTransaction.toString());

        // Start of a Transaction
        driver.executeScript("seetest:client.startPerformanceTransaction(\"1.0\")");

        driver.findElement(By.id("login_field")).sendKeys("rahee.khan@digital.ai");
        driver.findElement(By.id("password")).sendKeys("dummypassword");
        driver.findElement(By.name("commit")).click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='flash flash-full flash-error ']")));

        // End  of a Transaction
        Object loginTransaction = driver.executeScript("seetest:client.endPerformanceTransaction(\"GitHub_Error_Message_Display\")");
        System.out.println(loginTransaction.toString());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
