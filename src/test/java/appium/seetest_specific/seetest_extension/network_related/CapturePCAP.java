package appium.seetest_specific.seetest_extension.network_related;

import com.experitest.appium.SeeTestClient;
import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
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

public class CapturePCAP {

    protected AndroidDriver<AndroidElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected SeeTestClient client;

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("udid", "R58M54S7TET");
        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
        client = new SeeTestClient(driver);
    }

    @Test
    public void capture_pcap() {
        client.startCaptureNetworkDump(System.getProperty("user.dir") + "\\resources\\pcap_files\\file.pcap");
        driver.context("WEBVIEW_1");
        driver.navigate().to("https://google.com");
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.name("q"))).click();
        client.stopCaptureNetworkDump();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
