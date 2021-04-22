package appium.seetest_specific.ada.audio;

import com.experitest.appium.SeeTestClient;
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

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");

        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    // https://docs.experitest.com/pages/viewpage.action?spaceKey=TET&title=SendKeysWithBT
    @Test
    public void testing() {

        // Validate audio file each interaction
        // Any interaction needs to happen through BT Commands

        // Enable VoiceOver / TalkBack (Make sure its always on)
        // Start Application
        // Go to next element / send text
        // Validate you are on that element (Need to listen / understand where I am)
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
