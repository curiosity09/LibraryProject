package main.by.library.jdbs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

import static main.by.library.util.LoggerUtil.*;

public final class PropertiesManager {

    private static final Properties properties = new Properties();
    private static final Logger LOGGER = LogManager.getLogger(PropertiesManager.class);

    private PropertiesManager() {
        throw new UnsupportedOperationException();
    }

    static {
        try {
            properties.load(PropertiesManager.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            LOGGER.error(ERROR_DURING_PROPERTIES_LOADING_MESSAGE, e);
        }
    }

    /**
     * Returns properties by the entered key
     * @param key String
     * @return properties String
     */
    public static String getPropertyByKey(String key) {
        return properties.getProperty(key);
    }
}
