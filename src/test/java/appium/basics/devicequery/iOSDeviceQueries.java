package appium.basics.devicequery;

import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class iOSDeviceQueries {

    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected IOSDriver<IOSElement> driver = null;

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));

        /**
         * ================================
         *  Device Query Samples          =
         * ================================
         */
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'"); // Find ANY iPhone
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='TABLET'"); // Find ANY iPad
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and contains(@version, '13.2')"); // Find an iOS Device containing OS Version 13.2
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and starts-with(@name, 'iPhone 11')"); // Find an iOS Device starting with name "iPhone 11"
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @emulator='true'"); // Find ANY iOS Simulator

        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void device_queries_ios() throws InterruptedException {
        driver.navigate().to("https://google.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
