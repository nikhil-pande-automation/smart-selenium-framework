package com.nikhil.framework.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads test data from a properties file.
 * <p>
 * We already have ConfigReader. Still maintaining another PropertiesReader.
 * ConfigReader: Reads framework configuration
 * PropertiesReader: Reads test data.
 */

public final class PropertiesReader implements TestDataReader {

    // constructors were not private Since the method is no longer static. We need an object now.
    public PropertiesReader() {
    }

    @Override
    public String getValue(String filePath, String key) {
        {
            Properties properties = new Properties();

            try (FileInputStream input = new FileInputStream(filePath)) {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Unable to read properties file : " + filePath, e);
            }
            String value = properties.getProperty(key);
            if (value == null) {

                throw new RuntimeException(
                        "Key not found : " + key);

            }
            return value.trim();

        }
    }
}
