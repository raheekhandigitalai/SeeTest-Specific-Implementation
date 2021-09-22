package appium.basics.devicequery;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DeviceQueries {

    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

    public void testing() {
//        desiredCapabilities.setCapability("udid", "af4048227396b952083a825efa2796e62e03984d");

        desiredCapabilities.setCapability("deviceQuery", "@os='ios' and contains(@model, 'iPhone X')");
    }

}
