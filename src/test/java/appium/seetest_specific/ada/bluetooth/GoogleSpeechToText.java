package appium.seetest_specific.ada.bluetooth;

import com.experitest.appium.SeeTestClient;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GoogleSpeechToText {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * This will work ASSUMING you have VoiceOver enabled on the iOS Device already.
     *
     * In the class VoiceOverAutomation.java we looked at how to automate
     * VoiceOver approach. How to capture audio and perform Voice Over gestures.
     *
     * The next challenge that rises is, how do you know where you are on the device
     * or at any given page, since the test is running as an Automation script, you'd
     * expect the test to run without constant monitoring.
     *
     * While there are few ways, one way would be to convert the audio to a plain
     * human readable String, and process that mid script.
     *
     * Google allows a way to accurately (most of the time) converts speech into text using an
     * API powered by Google’s AI technologies.
     *
     * https://cloud.google.com/speech-to-text
     *
     * There are some pre-requisites in order to get this working, reference
     * to their Quick Start guide:
     *
     * https://cloud.google.com/speech-to-text/docs/quickstart-gcloud
     *
     * There are also some Dependencies needed from Google:
     *
     *  <dependencyManagement>
     *         <dependencies>
     *             <dependency>
     *                 <groupId>com.google.cloud</groupId>
     *                 <artifactId>libraries-bom</artifactId>
     *                 <version>20.2.0</version>
     *                 <type>pom</type>
     *                 <scope>import</scope>
     *             </dependency>
     *         </dependencies>
     *     </dependencyManagement>
     *
     *     <dependencies>
     *         <dependency>
     *             <groupId>com.google.cloud</groupId>
     *             <artifactId>google-cloud-speech</artifactId>
     *         </dependency>
     *     </dependencies>
     *
     */

    protected IOSDriver<IOSElement> driver = null;
    protected DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected SeeTestClient client;

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        desiredCapabilities.setCapability("testName", method.getName());
        desiredCapabilities.setCapability("accessKey", new PropertiesReader().getProperty("seetest.accesskey"));
//        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and @category='PHONE'");
        desiredCapabilities.setCapability("udid", "00008020-001C61863CE9002E");
        desiredCapabilities.setCapability("app", "cloud:com.experitest.ExperiBank");
        desiredCapabilities.setCapability("bundleId", "com.experitest.ExperiBank");

        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("cloud.url")), desiredCapabilities);
        client = new SeeTestClient(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void record_and_validate() throws InterruptedException {

        String firstElementAudioFile = System.getProperty("user.dir") + "\\resources\\audio_recordings\\first_element.wav";
        boolean usernameTextFieldExists = goToNextElement(firstElementAudioFile, "usernametextfield");

        if (usernameTextFieldExists) {

            tapOnElement();
            String currentItem = readSelectedItem(System.getProperty("user.dir") + "\\resources\\audio_recordings\\read_selected_item.wav");
            System.out.println(currentItem);

            String secondElementAudioFile = System.getProperty("user.dir") + "\\resources\\audio_recordings\\second_element.wav";
            boolean passwordTextFieldExists = goToNextElement(secondElementAudioFile, "passwordtextfield");

            if (passwordTextFieldExists) {

            }

        }
    }

    public String readSelectedItem(String localFilePath) throws InterruptedException {
        startAudioRecording(localFilePath);
        sendKeysWithBT("" + Keys.CONTROL+ Keys.ALT + "A");
        Thread.sleep(6000);
        stopAudioRecording();

        String currentItem = "";
        currentItem = whatIsCurrentItem(localFilePath);
        return currentItem;
    }

    public void tapOnElement() {
        sendKeysWithBT("" + Keys.CONTROL + Keys.ALT + " ");
    }

    public void goToPreviousElement() {
        sendKeysWithBT("" + Keys.ARROW_LEFT);
    }

    public boolean goToNextElement(String localFilePath, String itemToFind) throws InterruptedException {
        startAudioRecording(localFilePath);
        sendKeysWithBT("" + Keys.RIGHT);
        Thread.sleep(6000);
        stopAudioRecording();

        boolean doesItemExist = doesItemExist(itemToFind, localFilePath);
        return doesItemExist;
    }

    public void sendKeysWithBT(Object... args) {
        driver.executeScript("seetest:client.sendKeysWithBT", args);
    }

    public void startAudioRecording(String localFilePath) {
        client.startAudioRecording(localFilePath);
    }

    public void stopAudioRecording() {
        client.stopAudioRecording();
    }

    public String whatIsCurrentItem(String localFilePath) {
        String item = "";
        ArrayList arrayList = convertWavAudioFileToReadableText(localFilePath);
        item = arrayList.toString();
        return item;
    }

    public boolean doesItemExist(String voiceOverText, String localFilePath) {
        // Passing in the audio file, making it readable and storing it in an i.e. ArrayList.
        ArrayList arrayList = convertWavAudioFileToReadableText(localFilePath);
        for (int i = 0; i < arrayList.size(); i++) {
//            System.out.printf("Transcript: %s\n", arrayList.get(i).toString().replaceAll("\\s", ""));
            if (arrayList.get(i).toString().replaceAll("\\s", "").contains(voiceOverText)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList convertWavAudioFileToReadableText(String localFilePath) {
        ArrayList arrayList = new ArrayList();
        try (SpeechClient speechClient = SpeechClient.create()) {

            // The language of the supplied audio
            String languageCode = "en-US";

            // Sample rate in Hertz of the audio data sent
            int sampleRateHertz = 44100;

            // Encoding of audio data sent. This sample sets this explicitly.
            // This field is optional for FLAC and WAV audio formats.
            RecognitionConfig.AudioEncoding encoding = RecognitionConfig.AudioEncoding.LINEAR16;
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setLanguageCode(languageCode)
                            .setSampleRateHertz(sampleRateHertz)
                            .setEncoding(encoding)
                            .setAudioChannelCount(2)
                            .build();
            Path path = Paths.get(localFilePath);
            byte[] data = Files.readAllBytes(path);
            ByteString content = ByteString.copyFrom(data);
            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(content).build();
            RecognizeRequest request =
                    RecognizeRequest.newBuilder().setConfig(config).setAudio(audio).build();
            RecognizeResponse response = speechClient.recognize(request);
            for (SpeechRecognitionResult result : response.getResultsList()) {
                // First alternative is the most probable result
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                arrayList.add(alternative.getTranscript());
            }
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
        return arrayList;
    }

}
