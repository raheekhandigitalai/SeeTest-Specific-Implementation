package appium.seetest_specific.seetest_extension.network_related;

import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class SimulateNetworkCondition {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This approach is ALSO applicable for AndroidDriver.
     *
     * When running an automated test on either a Mobile or Web Application, you may want to know how the Application
     * behaves under different network conditions.
     *
     * Given that the iOS / Android Devices are always setup under a perfect and stable WiFi environment, we may want to
     * change the condition to be Lossy, High Latency, Bad Connection, and so on.
     *
     * We can either set the Network Condition at the beginning of the test, as part of our Desired Capabilities, or we can
     * also use a custom SeeTest command that allows us to set Network Condition mid test, and switch to a different / on / off as needed.
     *
     * Using Desired Capabilities:
     *
     * desiredCapabilities.setCapability("nvProfile", "4G-Lossy");
     *
     * Using SeeTest command mid test:
     *
     * driver.executeScript("seetest:client.setNetworkConditions(\"4g-Lossy\", \"0\")"); // Second parameter allows us to specify duration in milliseconds for the selected profile. 0 being unlimited.
     *
     * List of generic Network Profiles that can be used (custom profiles can be created as a Cloud Administrator):
     *
     * 4G-average
     * 4G-Lossy
     * 4G-High-Latency
     * 4G-bad-connection
     * 3G-average
     * 3G-Lossy
     * 3G-High-Latency
     * 3G-bad-connection
     *
     * https://docs.experitest.com/display/TE/Network+Virtualization+Capabilities
     * https://docs.experitest.com/display/TE/SetNetworkConditions
     */

    protected IOSDriver<IOSElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");

        desiredCapabilities.setCapability("nvProfile", "4G-Lossy"); // Apply a Network Profile

        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void simulate_network_condition() {
        driver.rotate(ScreenOrientation.PORTRAIT);

        // Apply a Network Profile
        driver.executeScript("seetest:client.setNetworkConditions(\"4G-Lossy\", \"0\")");

        driver.findElement(By.id("usernameTextField")).sendKeys("company");
        driver.findElement(By.id("passwordTextField")).sendKeys("company");
        driver.findElement(By.id("loginButton")).click();

        // Apply a Network Profile
        driver.executeScript("seetest:client.setNetworkConditions(\"4G-High-Latency\", \"0\")");

        driver.findElement(By.id("makePaymentButton")).click();
        driver.findElement(By.id("phoneTextField")).sendKeys("0541234567");
        driver.findElement(By.id("nameTextField")).sendKeys("Jon Snow");
        driver.findElement(By.id("amountTextField")).sendKeys("50");
        driver.findElement(By.id("countryButton")).click();
        driver.findElement(By.id("Switzerland")).click();
        driver.findElement(By.id("sendPaymentButton")).click();
        driver.findElement(By.id("Yes")).click();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
