package appium.basics.applicationsetup;

import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class AndroidApplicationSetup {

    protected AndroidDriver<AndroidElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE'");

        desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        desiredCapabilities.setCapability("appBuildVersion", "4352");
        desiredCapabilities.setCapability("appReleaseVersion", "1.01");
        desiredCapabilities.setCapability("installOnlyForUpdate", true);

        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
