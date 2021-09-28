package appium.oss.basics.applicationsetup;

import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

public class AndroidApplicationSetup {

    protected AndroidDriver<AndroidElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE'");

        desiredCapabilities.setCapability("automationName", "UIAutomator2");
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appiumVersion", "1.20.2");

        desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");

        driver = new AndroidDriver<AndroidElement>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }

    @Test
    public void login_experibank() throws InterruptedException {
        driver.findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/usernameTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/passwordTextField']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/loginButton']")).click();

        Thread.sleep(3000);

        AndroidElement makePaymentButton = driver.findElement(By.xpath("//*[@resource-id='com.experitest.ExperiBank:id/makePaymentButton']"));
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(makePaymentButton));

        assertEquals(true, makePaymentButton.isEnabled());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
