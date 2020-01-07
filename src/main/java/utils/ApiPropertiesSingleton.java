package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiPropertiesSingleton extends Properties {

    protected static ApiPropertiesSingleton instance;

    public static ApiPropertiesSingleton getInstance() {
        if (instance == null) {
            instance = new ApiPropertiesSingleton();
            try {
                instance.load(new FileInputStream(
                        "src/main/resources/api.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
