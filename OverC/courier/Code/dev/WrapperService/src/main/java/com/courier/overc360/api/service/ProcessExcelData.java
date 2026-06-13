package com.courier.overc360.api.service;


import com.courier.overc360.api.batch.dto.ConsignmentDto;
import com.courier.overc360.api.batch.dto.PieceDto;
import com.courier.overc360.api.model.lastmile.Delivery;
import com.courier.overc360.api.model.transaction.Console;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class ProcessExcelData {


    // Set String Value
    private String getValue(List<String> list, int index) {
        return list.size() > index ? list.get(index) : "";
    }

    // New method to convert String to Double
    private Double convertToDouble(String value) {
        try {
            return value.isEmpty() ? null : Double.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // New method to convert String to Long
    private Long convertToLong(String value) {
        try {
            return value.isEmpty() ? null : Long.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Excel Data Process
//    List<Console> prepConsoleData(List<List<String>> allRowsList) {
//        List<Console> consoleList = new ArrayList<>();
//        for (List<String> listUploadedData : allRowsList) {
//            Console console = new Console();
//            console.setConsoleId();
//            console.setPartnerHouseAirwayBill(getValue(listUploadedData, 0));
//            console.setAirportOriginCode(getValue(listUploadedData, 1));
//            console.setCountryOfOrigin(getValue(listUploadedData, 2));
//            console.setShipperName(getValue(listUploadedData, 3));
//            console.setGrossWeight(convertToDouble(getValue(listUploadedData, 4)));
//            console.setManifestedGrossWeight(convertToDouble(getValue(listUploadedData, 4)));
//            console.setNoOfPieces(getValue(listUploadedData, 5));
//            console.setDescription(getValue(listUploadedData, 6));
//            console.setConsigneeName(getValue(listUploadedData, 7));
//            console.setCurrency(getValue(listUploadedData, 8));
//            console.setCustomsValue(convertToDouble(getValue(listUploadedData, 9)));
//            console.setCustomsKd(getValue(listUploadedData, 10));
//            console.setIata(convertToDouble(getValue(listUploadedData, 11)));
//            console.setHsCode(getValue(listUploadedData, 12));
//            console.setPartnerMasterAirwayBill(getValue(listUploadedData, 13));
//            console.setConsoleId(getValue(listUploadedData, 14));
//            consoleList.add(console);
//        }
//        return consoleList;
//    }

    List<Console> prepConsoleData(List<Map<Integer, List<List<String>>>> allSheetsData) {
        List<Console> consoleList = new ArrayList<>();
        for (Map<Integer, List<List<String>>> sheetData : allSheetsData) {
            for (Map.Entry<Integer, List<List<String>>> entry : sheetData.entrySet()) {
                int sheetNumber = entry.getKey();  // Use sheet number as consoleId increment
                List<List<String>> allRowsList = entry.getValue();

                for (List<String> listUploadedData : allRowsList) {
                    Console console = new Console();
                    console.setLineNumber(convertToLong(getValue(listUploadedData, 0)));
                    console.setPartnerHouseAirwayBill(getValue(listUploadedData, 1));
                    console.setAirportOriginCode(getValue(listUploadedData, 2));
                    console.setCountryOfOrigin(getValue(listUploadedData, 3));
                    console.setShipperName(getValue(listUploadedData, 4));
                    console.setGrossWeight(convertToDouble(getValue(listUploadedData, 5)));
                    console.setManifestedGrossWeight(convertToDouble(getValue(listUploadedData, 5)));
                    console.setNoOfPieces(getValue(listUploadedData, 6));
                    console.setDescription(getValue(listUploadedData, 7));
                    console.setConsigneeName(getValue(listUploadedData, 8));
                    console.setCurrency(getValue(listUploadedData, 9));
                    console.setCustomsValue(convertToDouble(getValue(listUploadedData, 10)));
                    console.setCustomsKd(getValue(listUploadedData, 11));
                    console.setIata(convertToDouble(getValue(listUploadedData, 12)));
                    console.setHsCode(getValue(listUploadedData, 13));
                    console.setPartnerMasterAirwayBill(getValue(listUploadedData, 14));
                    console.setConsoleId(String.valueOf(sheetNumber));
                    consoleList.add(console);
                }
            }
        }
        return consoleList;
    }

    // Piece Table
    List<PieceDto> prepPieceData(List<List<String>> allRowList) {
        List<PieceDto> pieceDtoList = new ArrayList<>();
        for (List<String> listUploadedData : allRowList) {
            PieceDto pieceDto = new PieceDto();
            pieceDto.setPieceId(getValue(listUploadedData, 0));
            pieceDto.setPartnerHouseAirwayBill(getValue(listUploadedData, 1));
            pieceDto.setHouseAirwayBill(getValue(listUploadedData, 2));
            pieceDto.setPackReferenceNumber(getValue(listUploadedData, 3));
            pieceDto.setPieceTypeDescription(getValue(listUploadedData, 4));
            pieceDto.setDeclaredValue(convertToDouble(getValue(listUploadedData, 5)));
            pieceDto.setCodAmount(getValue(listUploadedData, 6));
            pieceDto.setLength(convertToDouble(getValue(listUploadedData, 7)));
            pieceDto.setDimensionUnit(getValue(listUploadedData, 8));
            pieceDto.setWidth(convertToDouble(getValue(listUploadedData, 9)));
            pieceDto.setHeight(convertToDouble(getValue(listUploadedData, 10)));
            pieceDto.setWeight(convertToDouble(getValue(listUploadedData, 11)));
            pieceDto.setWeight_unit(getValue(listUploadedData, 12));
            pieceDtoList.add(pieceDto);
        }
        return pieceDtoList;
    }

    public List<Delivery> uploadExcelFile(MultipartFile file, String companyId, String languageId) throws IOException {

        List<Delivery> deliveryList = new ArrayList<>();
        // Create Workbook for Excel file
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        //Get the header row first row
        Row headerRow = sheet.getRow(0);
        // Map column names their corresponding index
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (Cell cell : headerRow) {
            columnIndexMap.put(cell.getStringCellValue().toLowerCase().trim(), cell.getColumnIndex());
        }

        // Iterate through rows (skip the header row)
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Delivery delivery = new Delivery();

                // Set the fields dynamically based on column name
                setFieldByColumnName(delivery, row, columnIndexMap);
                delivery.setCompanyId(companyId);
                delivery.setLanguageId(languageId);

                // Add the mapped delivery object to the list
                deliveryList.add(delivery);
            }
        }

        // Close the workbook to free resource
        workbook.close();
        return deliveryList;
    }

    public void setFieldByColumnName(Delivery delivery, Row row, Map<String, Integer> columnIntexMap) {
        try {
            for (Map.Entry<String, Integer> entry : columnIntexMap.entrySet()) {

                String columnName = entry.getKey();
                Integer columnIndex = entry.getValue();
                Cell cell = row.getCell(columnIndex);

                switch (columnName) {
                    case "pieceid":
                        invokeSetter(delivery, "setPieceId", getCellValueAsString(cell));
                        break;
                    case "houseairwaybill":
                        invokeSetter(delivery, "setHouseAirwayBill", getCellValueAsString(cell));
                        break;
                    case "piececount":
                        invokeSetter(delivery, "setPieceCount", getCellValueAsString(cell));
                        break;
                    case "description":
                        invokeSetter(delivery, "setDescription", getCellValueAsString(cell));
                        break;
                    case "codamount":
                        invokeSetter(delivery, "setCodAmount", getCellValueAsDouble(cell));
                        break;
                    case "codfavorof":
                        invokeSetter(delivery, "setCodFavorOf", getCellValueAsString(cell));
                        break;
                    case "codcollectionmode":
                        invokeSetter(delivery, "setCodCollectionMode", getCellValueAsString(cell));
                        break;
                    case "currency":
                        invokeSetter(delivery, "setCurrency", getCellValueAsString(cell));
                        break;
                    case "priority":
                        invokeSetter(delivery, "setPriority", getCellValueAsString(cell));
                        break;
                    case "deliverydatetime":
                        invokeSetter(delivery, "setDeliveryDateTime", getCellValueAsDate(cell));
                        break;
                    case "couriertype":
                        invokeSetter(delivery, "setCourierType", getCellValueAsString(cell));
                        break;
                    case "courierid":
                        invokeSetter(delivery, "setCourierId", getCellValueAsString(cell));
                        break;
                    case "deliverytimeslotstart":
                        invokeSetter(delivery, "setDeliveryTimeSlotStart", getCellValueAsDate(cell));
                        break;
                    case "deliverytimeslotend":
                        invokeSetter(delivery, "setDeliveryTimeSlotEnd", getCellValueAsDate(cell));
                        break;
                    case "emailid":
                        invokeSetter(delivery, "setEmailId", getCellValueAsString(cell));
                        break;
                    case "destcompany":
                        invokeSetter(delivery, "setCompany", getCellValueAsString(cell));
                        break;
                    case "name":
                        invokeSetter(delivery, "setName", getCellValueAsString(cell));
                        break;
                    case "phone":
                        invokeSetter(delivery, "setPhone", getCellValueAsString(cell));
                        break;
                    case "alternatephone":
                        invokeSetter(delivery, "setAlternatePhone", getCellValueAsString(cell));
                        break;
                    case "addressline1":
                        invokeSetter(delivery, "setAddressLine1", getCellValueAsString(cell));
                        break;
                    case "addressline2":
                        invokeSetter(delivery, "setAddressLine2", getCellValueAsString(cell));
                        break;
                    case "pincode":
                        invokeSetter(delivery, "setPinCode", getCellValueAsString(cell));
                        break;
                    case "district":
                        invokeSetter(delivery, "setDistrict", getCellValueAsString(cell));
                        break;
                    case "city":
                        invokeSetter(delivery, "setCity", getCellValueAsString(cell));
                        break;
                    case "state":
                        invokeSetter(delivery, "setState", getCellValueAsString(cell));
                        break;
                    case "country":
                        invokeSetter(delivery, "setCountry", getCellValueAsString(cell));
                        break;
                    case "latitude":
                        invokeSetter(delivery, "setLatitude", getCellValueAsDouble(cell));
                        break;
                    case "longitute":
                        invokeSetter(delivery, "setLongitude", getCellValueAsDouble(cell));
                        break;
                    case "partnertype":
                        invokeSetter(delivery, "setPartnerType", getCellValueAsString(cell));
                        break;
                    case "partnerid":
                        invokeSetter(delivery, "setPartnerId", getCellValueAsString(cell));
                        break;
                    case "partnername":
                        invokeSetter(delivery, "setPartnerName", getCellValueAsString(cell));
                        break;
                }
            }
        } catch (Exception e) {
            log.info("Delivery Upload Field Set Failed <----------------------------->" + e.getMessage());
        }
    }

    public void setPieceFieldColumnName(PieceDto piece, Row row, Map<String, Integer> columnIntexMap) {
        try {
            for (Map.Entry<String, Integer> entry : columnIntexMap.entrySet()) {
                String columnName = entry.getKey();
                Integer columnIndex = entry.getValue();
                Cell cell = row.getCell(columnIndex);

                switch (columnName) {
                    case "pieceid":
                        invokeSetter(piece, "setPieceId", getCellValueAsString(cell));
                        break;
                    case "partnerhouseairwaybill":
                        invokeSetter(piece, "setPartnerHouseAirwayBill", getCellValueAsString(cell));
                        break;
                    case "houseairwaybill":
                        invokeSetter(piece, "setHouseAirwayBill", getCellValueAsString(cell));
                        break;
                    case "packreferencenumber":
                        invokeSetter(piece, "setPackReferenceNumber", getCellValueAsString(cell));
                        break;
                    case "piecetypedescription":
                        invokeSetter(piece, "setPieceTypeDescription", getCellValueAsString(cell));
                        break;
                    case "declaredvalue":
                        invokeSetter(piece, "setDeclaredValue", getCellValueAsDouble(cell));
                        break;
                    case "codamount":
                        invokeSetter(piece, "setCodAmount", getCellValueAsString(cell));
                        break;
                    case "length":
                        invokeSetter(piece, "setLength", getCellValueAsDouble(cell));
                        break;
                    case "dimensionunit":
                        invokeSetter(piece, "setDimensionUnit", getCellValueAsString(cell));
                        break;
                    case "width":
                        invokeSetter(piece, "setWidth", getCellValueAsDouble(cell));
                        break;
                    case "height":
                        invokeSetter(piece, "setHeight", getCellValueAsDouble(cell));
                        break;
                    case "weight":
                        invokeSetter(piece, "setWeight", getCellValueAsDouble(cell));
                        break;
                    case "weightunit":
                        invokeSetter(piece, "setWeightUnit", getCellValueAsString(cell));
                        break;
                }
            }
        } catch (Exception ex) {
            log.info("Piece Update Upload file Failed " + ex.getMessage());
            throw ex;
        }
    }


    // Consignment_Upload_Update
    public void setConsignmentFieldColumnName(ConsignmentDto consignmentDto, Row row, Map<String, Integer> columnIntexMap) {
        try {
            for (Map.Entry<String, Integer> entry : columnIntexMap.entrySet()) {
                String columnName = entry.getKey();
                Integer columnIndex = entry.getValue();
                Cell cell = row.getCell(columnIndex);

                switch (columnName) {
                    case "houseairwaybill":
                        invokeSetter(consignmentDto, "setHouseAirwayBill", getCellValueAsString(cell));
                        break;
                    case "length":
                        invokeSetter(consignmentDto, "setLength", getCellValueAsDouble(cell));
                        break;
                    case "dimensionunit":
                        invokeSetter(consignmentDto, "setDimensionUnit", getCellValueAsString(cell));
                        break;
                    case "width":
                        invokeSetter(consignmentDto, "setWidth", getCellValueAsDouble(cell));
                        break;
                    case "height":
                        invokeSetter(consignmentDto, "setHeight", getCellValueAsDouble(cell));
                        break;
                    case "actualweight":
                        invokeSetter(consignmentDto, "setActualWeight", getCellValueAsDouble(cell));
                        break;
                    case "weightunit":
                        invokeSetter(consignmentDto, "setWeightUnit", getCellValueAsString(cell));
                        break;
                }
            }
        } catch (Exception ex) {
            log.info("Consignment Update Upload file Failed " + ex.getMessage());
            throw ex;
        }
    }

    // Piece SetFieldName
    public List<PieceDto> setPieceExcelFile(MultipartFile file) throws Exception {

        List<PieceDto> pieceDtoList = new ArrayList<>();
        // Create Excel
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        // Get the header first row
        Row headerRow = sheet.getRow(0);
        Map<String, Integer> columnIntexMap = new HashMap<>();
        for (Cell cell : headerRow) {
            columnIntexMap.put(cell.getStringCellValue().toLowerCase().trim(), cell.getColumnIndex());
        }
        // Skip the header First Row
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                PieceDto piece = new PieceDto();
                // Set value in field name
                setPieceFieldColumnName(piece, row, columnIntexMap);
                pieceDtoList.add(piece);
            }
        }
        workbook.close();
        return pieceDtoList;
    }

    // Piece SetFieldName
    public List<ConsignmentDto> setConsignmentExcelFile(MultipartFile file) throws Exception {

        List<ConsignmentDto> consignmentDtos = new ArrayList<>();
        // Create Excel
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        // Get the header first row
        Row headerRow = sheet.getRow(0);
        Map<String, Integer> columnIntexMap = new HashMap<>();
        for (Cell cell : headerRow) {
            columnIntexMap.put(cell.getStringCellValue().toLowerCase().trim(), cell.getColumnIndex());
        }
        // Skip the header First Row
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                ConsignmentDto consignmentDto = new ConsignmentDto();
                // Set value in field name
                setConsignmentFieldColumnName(consignmentDto, row, columnIntexMap);
//                consignmentDto.setGrossWeight(consignmentDto.getWeight());
//                consignmentDto.setNetWeight(consignmentDto.getWeight());
                consignmentDtos.add(consignmentDto);
            }
        }
        workbook.close();
        return consignmentDtos;
    }



    // Helper method to invoke the setter method using reflection
    private void invokeSetter(Object obj, String methodName, Object value) {
        try {
//            if (value != null) {
            Method method = obj.getClass().getMethod(methodName, value.getClass());
            method.invoke(obj, value);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        // Check if the cell is of numeric type and not a date
        if (cell.getCellType() == CellType.NUMERIC && !DateUtil.isCellDateFormatted(cell)) {
            // Format as a whole number to avoid scientific notation
            return new BigDecimal(cell.getNumericCellValue()).toPlainString();
        } else {
            // Otherwise, use the default string representation
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue().trim();
        }
    }

    private Double getCellValueAsDouble(Cell cell) {
        return cell != null && cell.getCellType() == CellType.NUMERIC ? cell.getNumericCellValue() : 0.0;
    }

    private Date getCellValueAsDate(Cell cell) {
        return cell != null && cell.getCellType() == CellType.NUMERIC ? cell.getDateCellValue() : null;
    }


}
