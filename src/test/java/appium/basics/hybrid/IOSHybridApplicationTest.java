package appium.basics.hybrid;

import com.experitest.appium.SeeTestClient;
import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class IOSHybridApplicationTest {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This approach is ALSO applicable for AndroidDriver.
     *
     * The Continuous Testing platform has the ability to easily test Hybrid Applications.
     * A Hybrid Application usually consists of both Native and Web components.
     *
     * In order for Appium to understand which context you are working with, it is necessary to
     * specify the context accordingly.
     *
     * Here are the contexts available:
     *
     * WEBVIEW_1 - This represents Web View context
     * NATIVE_APP - This represents Native context
     *
     * And here is how we would switch to relevant context:
     *
     * driver.context("WEBVIEW_1");
     * driver.context("NATIVE_APP");
     *
     */

    private IOSDriver<IOSElement> driver = null;
    private SeeTestClient client = null;
    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        driver = new IOSDriver<IOSElement>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
        client = new SeeTestClient(driver);
    }

    @Test
    public void ios_hybrid_test() {
        driver.context("NATIVE_APP");

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("usernameTextField")));

        driver.findElement(By.id("usernameTextField")).sendKeys("company");
        driver.findElement(By.id("passwordTextField")).sendKeys("company");
        driver.findElement(By.id("Login")).click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("logo.png")));

        driver.context("WEBVIEW_1");

        String balanceAmountInWebContext = driver.findElement(By.xpath("//H1[contains(text(), 'Your balance is')]")).getText();
        System.out.println(balanceAmountInWebContext);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
