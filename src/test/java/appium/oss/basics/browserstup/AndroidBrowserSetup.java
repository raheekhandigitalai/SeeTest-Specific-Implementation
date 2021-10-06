package appium.oss.basics.browserstup;

import com.experitest.reporter.testng.Listener;
import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static org.testng.Assert.assertEquals;

@Listeners(Listener.class)
public class AndroidBrowserSetup {

    protected AndroidDriver<AndroidElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "os='android'");

        desiredCapabilities.setCapability("appiumVersion", "1.20.2");
        desiredCapabilities.setCapability("automationName", "UIAutomator2");
        desiredCapabilities.setCapability("platformName", "Android");

        desiredCapabilities.setCapability("browserName", "Chrome");

        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void github_invalid_credentials_scenario() throws InterruptedException {

        driver.navigate().to("https://github.com/login");

        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            if (context.contains("WEB")) {
                driver.context(context);
                break;
            }
        }

        new WebDriverWait(driver, 10).pollingEvery(Duration.ofSeconds(2)).until(ExpectedConditions.elementToBeClickable(By.id("login_field")));

        driver.findElement(By.id("login_field")).sendKeys("rahee.khan@digital.ai");
        driver.findElement(By.id("password")).sendKeys("dummypassword");

        driver.findElement(By.name("commit")).click();

        Thread.sleep(5000);

        WebElement flashErrorCard = driver.findElement(By.xpath("//*[@class='flash flash-full flash-error ']"));
        assertEquals(true, flashErrorCard.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
