package com.nikhil.framework.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class JsonReader implements TestDataReader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String getValue(String filePath, String key) {

        try {
            JsonNode root = OBJECT_MAPPER.readTree(new File(filePath));
            JsonNode value = root.get(key);

            if (value == null) {
                throw new RuntimeException(
                        "Key not found : " + key);
            }
            return value.asText();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read JSON file : " + filePath, e);

        }
    }
}
