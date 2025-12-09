package aqa;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found in resources folder!");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String GetProperty(String key){
        return properties.getProperty(key);
    }
}
