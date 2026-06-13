package com.courier.overc360.api.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class ExcelReadMultiSheet {

    List<List<String>> readExcelData(File file) {
        try {
            Workbook workbook = new XSSFWorkbook(file);
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            List<List<String>> allRowsList = new ArrayList<>();
            DataFormatter fmt = new DataFormatter();

            // Iterate All Excel SheetData
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(sheetIndex);
                log.info("Processing Sheet: " + sheet.getSheetName());

                for (int rn = sheet.getFirstRowNum(); rn <= sheet.getLastRowNum(); rn++) {
                    List<String> listUploadData = new ArrayList<>();
                    Row row = sheet.getRow(rn);
                    log.info("Row:  " + (row != null ? row.getRowNum() : "null"));

                    if (row == null) {
                    } else if (row.getRowNum() != 0) {  // Skip the header row if needed
                        for (int cn = 0; cn <= row.getLastCellNum(); cn++) {
                            Cell cell = row.getCell(cn);
                            if (cell == null) {
                                log.info("Cell empty: " + cell);
                                listUploadData.add("");
                            } else {
                                String cellStr = fmt.formatCellValue(cell);
                                log.info("CellStr: " + cellStr);
                                listUploadData.add(cellStr);
                            }
                        }
                        allRowsList.add(listUploadData);
                    }
                }
            }
            log.info("List data: " + allRowsList);
            workbook.close();  // Ensure the workbook is closed after reading
            return allRowsList;
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    List<Map<Integer, List<List<String>>>> readExcelDataForConsole(File file) {
        try {
            Workbook workbook = new XSSFWorkbook(file);
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            DataFormatter fmt = new DataFormatter();
            List<Map<Integer, List<List<String>>>> allSheetsData = new ArrayList<>();

            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(sheetIndex);
                log.info("Processing Sheet: " + sheet.getSheetName());

                Map<Integer, List<List<String>>> sheetData = new HashMap<>();
                List<List<String>> allRowsList = new ArrayList<>();

                for (int rn = 7; rn < sheet.getLastRowNum(); rn++) {        // 7 row skip & last row skip
                    List<String> listUploadData = new ArrayList<>();
                    Row row = sheet.getRow(rn);

                    if (row != null) {
                        for (int cn = 0; cn <= row.getLastCellNum(); cn++) {
                            Cell cell = row.getCell(cn);
                            String cellStr = (cell == null) ? "" : fmt.formatCellValue(cell);
                            listUploadData.add(cellStr);
                        }
                        allRowsList.add(listUploadData);
                    }
                }
                sheetData.put(sheetIndex + 1, allRowsList);  // Map sheet index (1-based) to data
                allSheetsData.add(sheetData);
            }

            workbook.close();
            log.info("List data: " + allSheetsData);
            return allSheetsData;
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

}
