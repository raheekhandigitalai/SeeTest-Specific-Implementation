package appium.seetest_specific.seetest_extension.mockauthentication;

import com.experitest.appium.SeeTestClient;
import helpers.PropertiesReader;
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

import java.net.MalformedURLException;
import java.net.URL;

public class iOSMockAuthentication {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This approach is ALSO applicable for AndroidDriver.
     *
     * The Continuous Testing platform has the ability to automate a TouchID / FaceID
     * scenario. For an example, we want to validate how your Application responds
     * when different authentication types are sent to the Application.
     *
     * The following dependency can be added to your pom.xml file:
     *
     *     <dependency>
     *         <groupId>com.experitest</groupId>
     *         <artifactId>appium-seetest-extension</artifactId>
     *         <version>21.8</version>
     *     </dependency>
     *
     * Keeping in mind that this is not hosted on mvnrepository.com, so you'll need to add the
     * following repository block as well:
     *
     *    <repositories>
     *         <repository>
     *             <id>Experitest.repo1</id>
     *             <name>YourName</name>
     *             <url>https://uscloud.experitest.com/repo/</url>
     *             <layout>default</layout>
     *         </repository>
     *     </repositories>
     *
     * Once the Dependency is available, here is how you would initialize the SeeTest Client:
     *
     * SeeTestClient client;
     * AppiumDriver driver;
     *
     * driver = new AppiumDriver(new URL("cloud.url"), desiredCapabilities);
     * client = new SeeTestClient();
     *
     * This allows you to use custom SeeTest commands in combination with your Appium commands.
     *
     * https://docs.experitest.com/display/TE/SetAuthenticationReply
     *
     */

    private IOSDriver<IOSElement> driver = null;
    private SeeTestClient client = null;
    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        desiredCapabilities.setCapability("instrumentApp", true); // Instruments the Application, i.e. inject relevant libraries for operations such as TouchID Authentication
        driver = new IOSDriver<IOSElement>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
        client = new SeeTestClient(driver);
    }

    @Test
    public void ios_mock_authentication() {
        /** When installing and launching app with "instrumentApp" capability, the
         *  object spy will recognize elements in RED color. This is known as the "Instrumented Dump".
         *
         *  We want to switch to the "Non-Instrumented Dump" like below:
         */
        driver.context("NATIVE_APP");

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("usernameTextField")));

        driver.findElement(By.id("usernameTextField")).sendKeys("company");
        driver.findElement(By.id("passwordTextField")).sendKeys("company");
        driver.findElement(By.id("Login")).click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeButton[@id='Advanced Actions']")));
        driver.findElement(By.xpath("//XCUIElementTypeButton[@id='Advanced Actions']")).click();

        client.setAuthenticationReply("AuthenticationFailedError", 0);

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeButton[@id='Account Info']")));
        driver.findElement(By.xpath("//XCUIElementTypeButton[@id='Account Info']")).click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("OK")));
        driver.findElement(By.id("OK")).click();

        client.setAuthenticationReply("Success", 0);

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeButton[@id='Account Info']")));
        driver.findElement(By.xpath("//XCUIElementTypeButton[@id='Account Info']")).click();

        if (new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'John Doe')]"))).isDisplayed()) {
            System.out.println("Successful Authentication");
        } else {
            System.out.println("Failed Authentication");
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
