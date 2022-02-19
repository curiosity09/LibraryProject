package main.by.library.jdbs;

import main.by.library.util.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesManager implements LoggerUtil {

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
