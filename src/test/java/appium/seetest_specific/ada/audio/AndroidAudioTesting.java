package appium.seetest_specific.ada.audio;

import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Keys;
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
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE'");

        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void sendKeysWithBT() {
        driver.executeScript("seetest:client.sendKeysWithBT", "" + Keys.RIGHT);
        driver.executeScript("seetest:client.sendKeysWithBT", "" + Keys.CONTROL + Keys.ALT + " ");
    }

    @Test
    public void record_audio() {
        driver.executeScript("seetest:client.startAudioRecording(\"\")");
        driver.executeScript("seetest:client.stopAudioRecording()");
    }

    @Test
    public void play_audio_to_phone() {
        driver.executeScript("seetest:client.startAudioPlay(\"\")");
        driver.executeScript("seetest:client.stopAudioPlay()");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
