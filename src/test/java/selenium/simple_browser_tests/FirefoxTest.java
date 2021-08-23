package selenium.simple_browser_tests;

import helpers.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;

public class FirefoxTest {

    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected RemoteWebDriver driver = null;

    @BeforeMethod
    public void setUp(Method method) throws Exception {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "firefox");
        desiredCapabilities.setCapability(CapabilityType.BROWSER_VERSION, "91");
        driver = new RemoteWebDriver(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
    }


    @Test
    public void browserTestGoogleSearch() {
        driver.manage().window().maximize();

        for (int i = 0; i < 1; i++) {
            driver.get("https://www.google.com");
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
            WebElement searchBar = driver.findElement(By.name("q"));
            searchBar.click();
            searchBar.sendKeys("Experitest");
            searchBar.sendKeys(Keys.ENTER);
        }
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        System.out.println(driver.getCapabilities());
        driver.quit();
    }

}
