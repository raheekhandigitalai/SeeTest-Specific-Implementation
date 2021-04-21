package appium.oss;

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

public class iOSTest {

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
        desiredCapabilities.setCapability("dontGoHomeOnQuit", true);

        desiredCapabilities.setCapability("automationName", "XCUITest");
        desiredCapabilities.setCapability("appiumVersion", "1.18.2");
        desiredCapabilities.setCapability("platformName", "iOS");

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
