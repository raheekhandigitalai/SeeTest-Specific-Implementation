package appium.seetest_specific.file_repository;

import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class FileRepositoryTests {

    private AndroidDriver<AndroidElement> driver = null;
    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    private FileRepositoryAPIs fileRepository = new FileRepositoryAPIs();

    private String fileId;

    String PATH_ON_USER_MACHINE = System.getProperty("user.dir") + "\\resources\\file_repository_files\\digitalailogo.png";
    String PATH_TO_DOWNLOAD_ON_USER_MACHINE = System.getProperty("user.dir") + "\\resources\\file_repository_files\\downloads\\digitalailogo.png";

    @BeforeTest
    public void setUp() throws MalformedURLException {
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE' and contains(@name, 'Samsung')");
        driver = new AndroidDriver<>(new URL("https://uscloud.experitest.com/wd/hub"), desiredCapabilities);
    }

    @Test
    public void push_file() {
        fileId = fileRepository.uploadFile(PATH_ON_USER_MACHINE, "digitalai_logo", "digitalai_logo");
        driver.executeScript("seetest:client.pushFile", "/sdcard/DCIM/Camera/digitalai_logo.png", "cloud:digitalai_logo");
    }

    @Test
    public void pull_file() {
        driver.executeScript("seetest:client.pullFile", "/sdcard/DCIM/Camera/digitalai_logo.png", "cloud:digitalai_logo_2");
        fileRepository.downloadFile(fileId, PATH_TO_DOWNLOAD_ON_USER_MACHINE);
        fileRepository.removeFile(fileId);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
