package com.nikhil.framework.data;

/**
 * Common contract for all test data readers.
 * so that The test code does not change except for the filename.
 */

public interface TestDataReader {
    /**
     * Reads a value from the data source.
     *
     * @param filePath complete file path
     * @param key      data key
     * @return value
     */
    String getValue(String filePath, String key);
}

