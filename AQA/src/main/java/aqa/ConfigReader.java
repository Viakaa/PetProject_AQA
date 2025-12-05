package aqa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(new File("C:\\\\Users\\\\viket\\\\IdeaProjects\\\\PetProject_AQA\\\\AQA\\\\src\\\\main\\\\resources\\\\config.properties").getAbsoluteFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String GetProperty(String key){

        return properties.getProperty( key );
    }
}
