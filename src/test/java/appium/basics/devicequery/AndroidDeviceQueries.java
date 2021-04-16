package appium.basics.devicequery;

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
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and contains(@version, '7')"); // Find an Android Device containing OS Version 7
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and starts-with(@name, 'Samsung')"); // Find an Android Device starting with name "Samsung"
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @emulator='true'"); // Find ANY Android Emulator

        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void device_queries_android() throws InterruptedException {
        driver.navigate().to("https://google.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
