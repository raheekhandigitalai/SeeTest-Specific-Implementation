package appium.oss.basics.browserstup;

import helpers.PropertiesReader;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.remote.MobileBrowserType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class iOSBrowserSetup {

    private String accessKey = "eyJ4cC51Ijo3MzU0MjQsInhwLnAiOjIsInhwLm0iOiJNVFUzT0RZd016ZzFOek16TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4OTM5NjM4NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.GP0hK0o0j2WEKt-J0aXsVbu1tmt-PhWUryqluokszJk";
    protected IOSDriver<IOSElement> driver = null;
    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");

        desiredCapabilities.setCapability("appiumVersion", "1.20.2");
        desiredCapabilities.setCapability("automationName", "XCUITest");
        desiredCapabilities.setCapability("platformName", "iOS");

        desiredCapabilities.setCapability("browserName", "Safari");

        driver = new IOSDriver<IOSElement>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
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
