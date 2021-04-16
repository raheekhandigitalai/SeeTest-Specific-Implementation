package appium.basics.applicationsetup;

import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class iOSApplicationSetup {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * When running your Appium tests against Mobile Applications, you may want to utilize capabilities to better
     * control the session.
     *
     * Few of the commonly used capabilities:
     *
     * app - Application name, prefixed with "cloud:" tells the session that it should look for that Application in the SeeTestCloud instance
     * instrumentApp - Instruments the Application
     * noReset
     * fullReset
     * applicationClearData
     * appBuildVersion
     * appReleaseVersion
     * installOnlyForUpdate
     * dontGoHomeOnQuit
     *
     * iOS Only:
     * bundleId
     * autoAcceptAlerts
     * autoDismissAlerts
     *
     * Android Only:
     * appPackage
     * appActivity
     * autoGrantPermissions
     *
     * https://docs.experitest.com/display/TE/Application+Setup
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
        desiredCapabilities.setCapability("appBuildVersion", "1.0");
        desiredCapabilities.setCapability("appReleaseVersion", "1.0");
        desiredCapabilities.setCapability("installOnlyForUpdate", true);

        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void native_test() {

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}