package appium.seetest_specific.performance;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
import helpers.PropertiesReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CaptureHAR_ThroughPerformanceTransaction {

    protected AppiumDriver driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    String platformName = "Android";

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));

        if (platformName.equalsIgnoreCase("Android")) {

            desiredCapabilities.setCapability("deviceQuery", "@os='android' and @category='PHONE'");
            driver = new AndroidDriver(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);

        } else if (platformName.equalsIgnoreCase("iOS")) {

            desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
            driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);

        }

    }

    @Test
    public void download_har_test() throws Exception {
        driver.navigate().to("https://google.com");
        driver.context("WEBVIEW_1");

        driver.executeScript("seetest:client.startPerformanceTransaction(\"4G-Lossy\")");

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.name("q")));
        driver.findElement(By.name("q")).click();
        driver.getKeyboard().sendKeys("Digital AI");

        Object loginTransaction = driver.executeScript("seetest:client.endPerformanceTransaction(\"Search_On_Google\")");
        System.out.println(loginTransaction.toString());

        String[] array = loginTransaction.toString().split(",");

        String[] transactionId = {};

        transactionId = array[15].split(":");
        System.out.println(transactionId[1]);

        // https://docs.experitest.com/display/TE/Rest+API+-+Transactions#RestAPITransactions-DownloadHARfile
        downloadHarFile(Long.parseLong(transactionId[1]));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    public void downloadHarFile(long transactionId) throws Exception {
        String url = "https://uscloud.experitest.com/api/transactions/%d/har";

        url = String.format(url, transactionId);
        Unirest.get(url).queryString("token", new PropertiesReader().getProperty("seetest.accesskey")).asFile(System.getProperty("user.dir") + "\\resources\\har_files\\harfile.json");

//        url = String.format(url, transactionId);
//        HttpResponse<InputStream> response = Unirest.get(url)
//                .queryString("token", new PropertiesReader().getProperty("seetest.accesskey"))
//                .asBinary();
//        int status = response.getStatus();
//        Assert.assertEquals(200, status);
//        if (status == 200) {
//            List<String> disposition = response.getHeaders().get("Content-Disposition");
//            String fileName = "harfile";
//            if (disposition != null && !disposition.isEmpty()) {
//                fileName = disposition.get(0).split("=")[1];
//            }
////            fileName = "/tmp/" + fileName;
//            fileName = System.getProperty("user.dir") + "\\resources\\har_files\\" + fileName;
//            saveToFile(response.getBody(), fileName);
//            Assert.assertTrue(new File(fileName).exists());
        }
    }

//    static void saveToFile(InputStream in, String fileName) throws IOException {
//        try (OutputStream out = new FileOutputStream(fileName)) {
//            byte[] buffer = new byte[1024];
//            int readCount;
//            while ((readCount = in.read(buffer)) != -1) {
//                out.write(buffer, 0, readCount);
//            }
//        }
//    }

//}
