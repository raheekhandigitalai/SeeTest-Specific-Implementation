package appium.seetest_specific.report_related;

import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.Listeners;
import com.experitest.reporter.testng.Listener;

@Listeners(Listener.class)
public class TestNGListener {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This approach is ALSO applicable for AndroidDriver.
     *
     * During an automated test run, assertion or exceptions can be thrown outside any context of SeeTest.
     * The test may show as failed, but because the exception isn't happening in relation to SeeTest / Appium based commands,
     * the test is marked as Passed in the SeeTestReporter.
     *
     * For an example, the following scenario:
     *
     * int e = 1 / 0;
     *
     * This will throw an exception from the IDE, but SeeTest marks this as passed.
     *
     * Here's another scenario:
     *
     * try {
     *     driver.findElement(By.id(usernameField)).sendKeys();
     * } catch (Exception e) {
     *     // Do nothing
     * }
     *
     * If the step within the Try block fails, the code goes to the exception statement, but this will mark the test as Passed
     * from the IDE as the exception was handled.
     * This will mark the test as Failed in the SeeTestReporter.
     *
     * By using the Listener as per Line 6, we are directly listening to TestNG events, and based on the test result in the IDE,
     * is what we will show in the SeeTestReporter as well.
     *
     * The Listener can also be used in a parallel_simulator_and_physical_device.xml file, referenced like this:
     *
     *     <listeners>
     *         <listener class-name="com.experitest.reporter.testng.Listener"/>
     *     </listeners>
     *
     * https://docs.experitest.com/display/TE/Reporting+and+Client+Side+Failures
     *
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

        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void divided_by_zero() {
        int e = 1 / 0;
        System.out.println(e);
    }

    @Test
    public void try_catch() {
        try {
            driver.findElement(By.id("user")).sendKeys("company"); // Invalid ID
        } catch (Exception e) {
            driver.findElement(By.id("usernameTextField")).sendKeys("company"); // Valid ID
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
