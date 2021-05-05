package appium.seetest_specific.ada.audio;

import com.experitest.appium.SeeTestClient;
import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class iOSAudioTesting {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * StartAudioPlay
     * StopAudioPlay
     * WaitForAudioPlayEnd
     * StartAudioRecording
     * StopAudioRecording
     *
     */

    protected IOSDriver<IOSElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected SeeTestClient client;

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        desiredCapabilities.setCapability("app", "cloud:com.experitest.ExperiBank");
        desiredCapabilities.setCapability("bundleId", "com.experitest.ExperiBank");

        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
        client = new SeeTestClient(driver);
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
