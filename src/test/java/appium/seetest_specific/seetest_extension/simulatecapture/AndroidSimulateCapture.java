package appium.seetest_specific.seetest_extension.simulatecapture;

import appium.seetest_specific.file_repository.FileRepositoryAPIs;
import com.experitest.appium.SeeTestClient;
import helpers.PropertiesReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidSimulateCapture {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This approach is ALSO applicable for IOSDriver.
     *
     * We have the ability to automate your Check Deposit, QR Code scanning scenarios.
     * In order to achieve the same, we need to import an extra dependency to our project.
     *
     * The following dependency can be added to your pom.xml file:
     *
     *     <dependency>
     *         <groupId>com.experitest</groupId>
     *         <artifactId>appium-seetest-extension</artifactId>
     *         <version>21.4</version>
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
     * https://docs.experitest.com/display/TE/SimulateCapture
     *
     */

    private AndroidDriver<AndroidElement> driver = null;
    private SeeTestClient client = null;
    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
        desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE' and contains(@name, 'Samsung')");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.uicatalog/.MainActivity");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.uicatalog");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        desiredCapabilities.setCapability("instrumentApp", true); // Instruments the Application, i.e. inject relevant libraries for operations such as Check Deposit
        driver = new AndroidDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
        client = new SeeTestClient(driver);
    }

    @Test
    public void android_simulatecapture() throws InterruptedException {
        driver.context("NATIVE_APP"); // Setting the context to standard Non-Instrumented dump (Launching app as Instrumented will introduce another context, hence changing back)

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='Camera']")));
        driver.findElement(By.xpath("//*[@text='Camera']")).click();

        for (int i = 0; i < 2; i++) {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='permission_allow_button']")));
            driver.findElement(By.xpath("//*[@id='permission_allow_button']")).click();
            Thread.sleep(2000);
        }

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='scanner3']")));
        driver.findElement(By.xpath("//*[@id='scanner3']")).click();

        client.simulateCapture("C:\\Users\\RaheeKhan\\Documents\\images\\qr-code.jpg");
        Thread.sleep(10000);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
