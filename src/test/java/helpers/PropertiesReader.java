package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {

    public PropertiesReader() {}

    public String getProperty(String property) {
        Properties props = System.getProperties();

        try {
            props.load(new FileInputStream(new File(System.getProperty("user.dir") + "\\resources\\setup.properties")));
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return System.getProperty(property);
    }

}
