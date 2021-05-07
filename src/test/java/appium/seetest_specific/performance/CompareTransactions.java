package appium.seetest_specific.performance;

import helpers.PropertiesReader;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CompareTransactions {

    /**
     *
     * ==================================================
     *                      READ ME                     =
     * ==================================================
     *
     * You may for an example want to test the latest version of your Application, and how it
     * measures (CPU / Memory / Battery, Network Bandwith, Duration) and how this has changed
     * over the previous versions.
     *
     * Here's an example of a Request Body:
     *
     *      {
     *          "filter": [ ...<filter expression>... ],
     *          "baseKey": <appVersion>,
     *          "baseKeyValue": 7.0,
     *          "compareCount": 2 (6.8 / 6.9),
     *          "comparisonTargets": [
     *              {
     *                  "name": <string>,
     *                  "measure": <string>,
     *                  "acceptedChange": <numeric>
     *              }, ...
     *          ]
     *      }
     *
     *
     * https://docs.experitest.com/display/TE/Performance+Pipeline
     *
     */

    String baseUrl = "https://uscloud.experitest.com/reporter/api/transactions/compare";

    @Test
    public void compare_transactions() {
        String request = "{\n" +
                "\t\"filter\": [\"appName\", \"=\", \"com.experitest.ExperiBank\"],\n" +
                "\t\"baseKey\": \"appVersion\",\n" +
                "\t\"baseKeyValue\": \"3708\",\n" +
                "\t\"compareCount\": 1,\n" +
                "\t\"comparisonTargets\": [\n" +
                "\t\t{ \"name\": \"Login-Eribank-IOS\", \"measure\": \"cpuMax\", \"acceptedChange\": 10.0 },\n" +
                "\t\t{ \"name\": \"Login-Eribank-IOS\", \"measure\": \"memMax\", \"acceptedChange\": 10.0 },\n" +
                "\t\t{ \"name\": \"Login-Eribank-IOS\", \"measure\": \"batteryMax\", \"acceptedChange\": 10.0 },\n" +
                "\t\t{ \"name\": \"Login-Eribank-IOS\", \"measure\": \"totalDownloadedBytes\", \"acceptedChange\": 10.0 }\n" +
                "\t]\n" +
                "}";
        HttpResponse<String> response = Unirest.post(baseUrl + "?token=" + new PropertiesReader().getProperty("seetest.accesskey"))
                .header("Content-Type", "application/json")
                .body(request)
                .asString();
        int status = response.getStatus();
        Assert.assertEquals(200, status);
        if (status == 200) {
            System.out.println(response.getBody());
        }
    }

}
