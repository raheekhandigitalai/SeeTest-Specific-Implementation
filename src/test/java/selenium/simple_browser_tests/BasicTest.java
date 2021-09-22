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

    RemoteWebDriver driver = null;
    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        desiredCapabilities.setCapability("accessKey", "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51IjoxMTIzMCwieHAucCI6MSwieHAubSI6MTYyOTM5NzU1ODYxMiwiZXhwIjoxOTQ0NzU3NTU4LCJpc3MiOiJjb20uZXhwZXJpdGVzdCJ9.Me8ULldF6-WYJup4aBXvmh1XSAShybt8v5BO9F6V3oQ");

        desiredCapabilities.setCapability("username", "rahee.khan");
        desiredCapabilities.setCapability("password", "");
//        desiredCapabilities.setCapability("projectName", "");

        desiredCapabilities.setCapability("browserName", "chrome");
        driver = new RemoteWebDriver(new URL("https://vpndemous3.experitest.com/wd/hub"), desiredCapabilities);
    }

    @Test
    public void github_login_invalid_credentials() throws InterruptedException {
        driver.navigate().to("https://github.com/login");
        driver.findElement(By.id("login_field")).sendKeys("rahee.khan@digital.ai");
        Thread.sleep(20000);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
