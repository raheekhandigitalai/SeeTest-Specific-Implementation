package appium;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class DemoClass {

    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    IOSDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        desiredCapabilities.setCapability("accessKey", "eyJ4cC51Ijo3MzU0MjQsInhwLnAiOjIsInhwLm0iOiJNVFUzT0RZd016ZzFOek16TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4OTM5NjM4NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.GP0hK0o0j2WEKt-J0aXsVbu1tmt-PhWUryqluokszJk");
//        desiredCapabilities.setCapability("udid", "00008020-000D59EC0250003A");

        desiredCapabilities.setCapability("deviceQuery", "@os='ios'");

        desiredCapabilities.setCapability("bundleId", "com.experitest.ExperiBank");
        desiredCapabilities.setCapability("dontGoHomeOnQuit", true);
        driver = new IOSDriver(new URL("https://uscloud.experitest.com/wd/hub"), desiredCapabilities);
    }

    @Test
    public void testing_01() throws InterruptedException {
        driver.findElement(By.name("usernameTextField")).sendKeys("dummyuser");
        driver.findElement(By.name("passwordTextField")).sendKeys("dummypassword");
        driver.findElement(By.name("Login")).click();
        Thread.sleep(10000);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
