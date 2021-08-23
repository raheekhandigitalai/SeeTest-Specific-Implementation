package appium.basics.devicequery;

import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class ParameterizedDeviceQueries {

     /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
      * By using @Parameter annotation in the setUp method, we are able to parameterize the "deviceQuery" capability
      * so that we can run this same Test Class against different set of devices, even scenarios like running this
      * same test against Physical Devices and Simulators.
      *
      * In the parallel_simulator_and_physical_device.xml file we've defined two test blocks:
      *
      *    <test name="1">
      *
      *         <parameter name="deviceQuery" value="@os='ios'" />
      *
      *         <classes>
      *             <class name="appium.basics.devicequery.iOSTest"/>
      *         </classes>
      *     </test>
      *
      *     <test name="2">
      *
      *         <parameter name="deviceQuery" value="@os='ios' and @emulator='true'" />
      *
      *         <classes>
      *             <class name="appium.basics.devicequery.iOSTest"/>
      *         </classes>
      *     </test>
     *
      * The first test will run on any available iOS device as per the Device Query.
      * The second test will run on any available iOS Simulator as per the Device Query.
     *
     */

    protected IOSDriver<IOSElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    @Parameters({"deviceQuery"})
    public void setUp(String deviceQuery, Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));

        desiredCapabilities.setCapability("deviceQuery", deviceQuery);
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");

        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void testing() {
        driver.context("NATIVE_APP");
        driver.findElement(By.name("usernameTextField")).sendKeys("company");
        driver.findElement(By.name("passwordTextField")).sendKeys("company");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
