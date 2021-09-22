package appium.seetest_specific.seetest_extension.device_related;

import com.experitest.appium.SeeTestClient;
import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class CaptureDeviceLogs {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This approach is ALSO applicable for IOSDriver.
     *
     * You may want to capture Network Transactions during your automated flow.
     * By using the following command:
     *
     *      client.startLoggingDevice(localPath);
     *
     * We start the capturing of network packets, until the following command is called:
     *
     *      client.stopLoggingDevice();
     *
     * https://docs.experitest.com/display/TE/StartLoggingDevice
     * https://docs.experitest.com/display/TE/SeeTest+Client+-+StopLoggingDevice
     *
     *
     * Prerequisites:
     *
     * You need to add the following section to your pom.xml:
     *
     *  <repositories>
     *         <repository>
     *             <id>Experitest.repo1</id>
     *             <name>YourName</name>
     *             <url>https://uscloud.experitest.com/repo/</url> - If you have a dedicated Cloud, update with your Cloud URL
     *             <layout>default</layout>
     *         </repository>
     *     </repositories>
     *
     *     <dependencies>
     *           <dependency>
     *                 <groupId>com.experitest</groupId>
     *                 <artifactId>appium-seetest-extension</artifactId>
     *                 <version>21.8</version>
     *             </dependency>
     *     </dependencies>
     *
     *     If using Gradle:
     *
     *     https://docs.experitest.com/pages/viewpage.action?pageId=55281084#AppiumSeeTestExtension(Java)-Gradle
     */

    protected AndroidDriver<AndroidElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected SeeTestClient client;

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='android'");
        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
        client = new SeeTestClient(driver);

        client.startLoggingDevice(System.getProperty("user.dir") + "\\resources\\devicelogs\\device_log.txt");
    }

    @Test
    public void capture_pcap() {
        driver.navigate().to("https://google.com");
        driver.context("WEBVIEW_1");
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.name("q"))).click();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        client.stopLoggingDevice();
        driver.quit();
    }

}
