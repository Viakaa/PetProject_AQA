package aqa.task16;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(new File("C:\\Users\\viket\\IdeaProjects\\JAVA_4AT_31\\java_aqa\\src\\main\\resources\\configurationProperty").getAbsoluteFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String GetProperty(String key){

        return properties.getProperty( key );
    }

    public static void main(String[] args) {
        GetProperty("trelloKey");
    }
}
