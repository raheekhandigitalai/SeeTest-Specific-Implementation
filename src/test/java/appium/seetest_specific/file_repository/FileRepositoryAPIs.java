package appium.seetest_specific.file_repository;

import helpers.PropertiesReader;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.io.File;

public class FileRepositoryAPIs {

    public String uploadFile(String filePathToImage, String description, String uniqueName) {
        HttpResponse<JsonNode> response = Unirest.post("https://uscloud.experitest.com/api/v1/files")
                .header("Authorization", "Bearer " + new PropertiesReader().getProperty("seetest.accesskey") + " ")
                .field("file", new File(filePathToImage))
                .field("description", description)
                .field("unique_name", uniqueName)
                .asJson();

        if (response.getStatus() == 200) {
            System.out.println("HTTP Status: " + response.getStatus());
            System.out.println("HTTP Body: " + response.getBody());
            JSONObject myObj = response.getBody().getObject();
            String id = myObj.getString("data");
            return id;
        } else {
            System.out.println("HTTP Status: " + response.getStatus());
            System.out.println("HTTP Body: " + response.getBody());
            return null;
        }
    }

    public void getFileInformation(String fileId) {

    }

    public void getFilesInformation() {

    }

    public void downloadFile(String fileId, String pathToDownload) {
        HttpResponse<File> response = Unirest.get("https://uscloud.experitest.com/api/v1/files/" + fileId + "/download")
                .header("Authorization", "Bearer " + new PropertiesReader().getProperty("seetest.accesskey") + " ")
                .asFile(pathToDownload);

        System.out.println("HTTP Status: " + response.getStatus());
        System.out.println("HTTP Body: " + response.getBody());
    }

    public void removeFile(String fileId) {
        HttpResponse<String> response = Unirest.delete("https://uscloud.experitest.com/api/v1/files/" + fileId)
                .header("Authorization", "Bearer " + new PropertiesReader().getProperty("seetest.accesskey") + " ")
                .asString();

        System.out.println("HTTP Status: " + response.getStatus());
        System.out.println("HTTP Body: " + response.getBody());
    }

    public void updateFile(String fileId) {

    }

}
