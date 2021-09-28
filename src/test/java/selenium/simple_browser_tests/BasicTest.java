package selenium.simple_browser_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class BasicTest {

    private RemoteWebDriver driver = null;
    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp() throws MalformedURLException {

        desiredCapabilities.setCapability("testName", "Chrome_GitHub");

        desiredCapabilities.setCapability("accessKey", "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51IjoyMTc4LCJ4cC5wIjoxLCJ4cC5tIjoxNjMxMTkzNzcxMTc2LCJleHAiOjE5NDY1NTM3NzEsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.dHZxU1VkSXvPcbrlHok5N06HJ8MSdDjZuqx9Ujz52Xs");
        desiredCapabilities.setCapability("browserName", "chrome");
        driver = new RemoteWebDriver(new URL("https://thrivent.experitest.com/wd/hub"), desiredCapabilities);
    }

    @Test
    public void github_login_invalid_credentials() throws InterruptedException {
        driver.manage().window().maximize();
        driver.navigate().to("https://github.com/login");
        driver.findElement(By.id("login_field")).sendKeys("rahee.khan@digital.ai");
        Thread.sleep(20000);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
