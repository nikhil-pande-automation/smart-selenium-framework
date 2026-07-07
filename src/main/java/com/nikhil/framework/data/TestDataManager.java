package com.nikhil.framework.data;

import com.nikhil.framework.constants.FrameworkConstants;

/**
 * Facade for accessing test data.
 *
 * Test classes should use this class instead of
 * directly calling PropertiesReader, JsonReader,
 * or ExcelReader.
 *
 *
 * LoginSteps
 *       │
 *       ▼
 * TestDataManager
 *       │
 *       ▼
 * Checks file extension
 *       │
 *       ├───────────────┬───────────────┐
 *       ▼               ▼               ▼
 * PropertiesReader  JsonReader    ExcelReader
 *       │               │               │
 *       ▼               ▼               ▼
 * .properties      .json          .xlsx
 */
public final class TestDataManager {

    private TestDataManager() {

    }

    /**
     * Returns value from any supported test data file.
     *
     * Supported formats:
     * - .properties
     * - .json
     * - .xlsx
     *
     * @param fileName Example:
     *                 registration.properties
     *                 registration.json
     *                 registration.xlsx
     * @param key Data key
     * @return data value
     *
     * So that The test code does not change except for the filename while reading actual data, For
     * Better understanding check 3demo classes are using excat same methods just file name changed.
     */
    public static String getValue(String fileName, String key) {

        String filePath = FrameworkConstants.TESTDATA_PATH + fileName;

        TestDataReader reader;

        if (fileName.endsWith(".properties")) {

            reader = new PropertiesReader();

        } else if (fileName.endsWith(".json")) {

            reader = new JsonReader();

        } else if (fileName.endsWith(".xlsx")) {

            reader = new ExcelReader();

        } else {

            throw new RuntimeException(
                    "Unsupported file type : " + fileName);

        }

        return reader.getValue(filePath, key);

    }

}