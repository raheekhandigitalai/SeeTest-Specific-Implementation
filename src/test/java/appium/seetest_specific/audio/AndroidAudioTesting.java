package appium.seetest_specific.audio;

import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class AndroidAudioTesting {

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

    protected AndroidDriver<AndroidElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));

        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");

        desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");

        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void record_audio() {

    }

    @Test
    public void play_audio_to_phone() {

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
