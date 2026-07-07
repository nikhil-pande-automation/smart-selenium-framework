package com.nikhil.framework.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Reads test data from Excel files.
 * <p>
 * Expected format:
 * <p>
 * Column A = Key
 * Column B = Value
 * <p>
 * We are NOT going to use row numbers.
 * We're designing a key-value based Excel, exactly like Properties and JSON
 * So that this keeps all three readers consistent.
 * We are not going to create a generic Excel utility that exposes rows, columns, and sheets directly.
 * Instead, we'll create an ExcelReader that also implements below method
 * String getValue(String filePath, String key)
 */
public final class ExcelReader implements TestDataReader {

    @Override
    public String getValue(String filePath, String key) {

        try (
                FileInputStream input = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(input)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {

                Cell keyCell = row.getCell(0);
                Cell valueCell = row.getCell(1);

                if (keyCell == null || valueCell == null) {
                    continue;
                }

                if (key.equalsIgnoreCase(keyCell.getStringCellValue().trim())) {
                    return valueCell.getStringCellValue().trim();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read Excel file : " + filePath, e);
        }

        throw new RuntimeException("Key not found : " + key);

    }

}