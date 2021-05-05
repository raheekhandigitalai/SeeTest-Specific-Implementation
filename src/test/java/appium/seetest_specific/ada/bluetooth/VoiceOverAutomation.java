package appium.seetest_specific.ada.bluetooth;

import com.experitest.appium.SeeTestClient;
import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.aspectj.lang.annotation.After;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class VoiceOverAutomation {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This will work ASSUMING you have VoiceOver enabled on the iOS Device already.
     *
     * If a device is configured with Bluetooth Adapter, we have the ability to perform VoiceOver gestures
     * through Keyboard shortcuts, as per this doc: https://docs.experitest.com/display/LT/Keyboard+shortcuts+for+VoiceOver+and+Talkback
     *
     * In our 21.4 Release, we introduced a way to perform the keyboard shortcuts through Automation as well, so that as a tester,
     * you can fully automate a VoiceOver flow, and capture the Audio for validating the output.
     *
     * In order to start Audio Recording, there is a need to include a dependency into the Project, which is defined like this in the pom.xml:
     *
     *      <repositories>
     *         <repository>
     *             <id>Experitest.repo1</id>
     *             <name>YourName</name>
     *             <url>https://uscloud.experitest.com/repo/</url>
     *             <layout>default</layout>
     *         </repository>
     *     </repositories>
     *
     *     <dependencies>
     *           <dependency>
     *             <groupId>com.experitest</groupId>
     *             <artifactId>appium-seetest-extension</artifactId>
     *             <version>21.4</version>
     *         </dependency>
     *     </dependencies>
     *
     * The way to define the "SeeTestClient" is like this:
     *
     *      IOSDriver driver = new IOSDriver(new URL("cloud.url"), desiredCapabilities);
     *      SeeTestClient client = new SeeTestClient(driver);
     *
     * And the available commands are:
     *
     *      client.startAudioRecording(String audioFile);
     *      client.stopAudioRecording();
     *      client.startAudioPlay(String audioFile);
     *      client.stopAudioPlay();
     *      client.waitForAudioPlayEnd(int timeout);
     *
     *      https://docs.experitest.com/display/TE/Audio+Support
     *
     *
     *  In order to mimic a VoiceOver gesture, we can use the following approach:
     *
     *      driver.executeScript("seetest:client.sendKeysWithBT", "" + Keys.RIGHT);
     *
     *      https://docs.experitest.com/display/TE/SendKeysWithBT
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

    @Test
    public void record_audio_while_doing_voice_over() throws InterruptedException {
        client.startAudioRecording(System.getProperty("user.dir") + "\\resources\\audio_recordings\\audio.wav");
        for (int i = 0; i < 4; i++) {
            driver.executeScript("seetest:client.sendKeysWithBT", "" + Keys.RIGHT);
            Thread.sleep(6000);
        }
        client.stopAudioRecording();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
