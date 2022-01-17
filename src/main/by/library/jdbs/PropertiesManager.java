package main.by.library.jdbs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class PropertiesManager {

    private PropertiesManager(){
        throw new UnsupportedOperationException();
    }

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(Files.newInputStream(Path.of("resources", "db.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyByKey(String key){
        return properties.getProperty(key);
    }
}
