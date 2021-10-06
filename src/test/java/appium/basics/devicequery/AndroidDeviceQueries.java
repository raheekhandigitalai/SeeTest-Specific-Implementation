package appium.basics.devicequery;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class AndroidDeviceQueries {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * DeviceQuery is a unique Capability to provide flexibility for Test Execution.
     * When triggering an automated test in Appium, you target a device by using the
     * following Capability:
     *
     * dc.setCapability(MobileCapabilityType.UDID, <>);
     *
     * With Device Query capability, you can target generic devices, here's an example:
     *
     * dc.setCapability("deviceQuery", "@os='android'");
     *
     * This targets ANY available Android Device. Here is another example:
     *
     * dc.setCapability("deviceQuery", "@os='android' and contains(@version, '8.1')");
     *
     * This targets ANY available Android 8.1 Device.
     *
     * The Device Query syntax is similar to XPATH, we can do things like:
     * starts-with, contains, and, or
     *
     * Full list of supported properties can be found here:
     * https://docs.experitest.com/display/TE/Device+Queries
     *
     */

    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected AndroidDriver<AndroidElement> driver = null;

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));

        /**
         * ================================
         *  Device Query Samples          =
         * ================================
         */
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE'"); // Find ANY Android Phone
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='TABLET'"); // Find ANY Android Tablet
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and contains(@version, '7.0')"); // Find an Android Device containing OS Version 7
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and starts-with(@name, 'Samsung')"); // Find an Android Device starting with name "Samsung"
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @emulator='true'"); // Find ANY Android Emulator

        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void device_queries_android() {
        driver.navigate().to("https://google.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
