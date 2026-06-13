package com.tekclover.wms.core.service;

import com.tekclover.wms.core.config.PropertiesConfig;
import com.tekclover.wms.core.exception.BadRequestException;
import com.tekclover.wms.core.model.transaction.*;
import com.tekclover.wms.core.model.warehouse.inbound.WarehouseApiResponse;
import com.tekclover.wms.core.model.warehouse.inbound.almailem.*;
import com.tekclover.wms.core.model.warehouse.outbound.almailem.*;
import com.tekclover.wms.core.model.warehouse.outbound.walkaroo.DeliveryConfirmationLineV3;
import com.tekclover.wms.core.model.warehouse.outbound.walkaroo.DeliveryConfirmationV3;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Service
public class FileStorageService {

	@Autowired
	PropertiesConfig propertiesConfig;

	@Autowired
	AuthTokenService authTokenService;

	//-----------------------------------------------------------------------------------
	@Autowired
	TransactionService transactionService;

	@Autowired
	EnterpriseSetupService enterpriseSetupService;
	//-----------------------------------------------------------------------------------

	private Path fileStorageLocation = null;
	private static final String UOM = "EACH";

	private String getTransactionAuthToken() {
		return authTokenService.getTransactionServiceAuthToken().getAccess_token();
	}
	private String getEnterpriseAuthToken() {
		return authTokenService.getEnterpriseServiceAuthToken().getAccess_token();
	}

	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	/**
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> storeFile(MultipartFile file) throws Exception {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return Collections.singletonMap("message", "File uploaded successfully!");
	}

	/**
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> processSOOrders(MultipartFile file) throws Exception {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if(allRowsList != null && !allRowsList.isEmpty()) {
				List<ShipmentOrderV2> shipmentOrders = prepSOData(allRowsList);
				log.info("shipmentOrders : " + shipmentOrders);
				WarehouseApiResponse dbWarehouseApiResponse = new WarehouseApiResponse();
				String authToken = getTransactionAuthToken();
				dbWarehouseApiResponse = transactionService.postShipmentOrderV2(shipmentOrders, authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	public Map<String, String> processSalesOrders(MultipartFile file) {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if(allRowsList != null && !allRowsList.isEmpty()) {
				List<SalesOrderV2> salesOrders = prepSalesOrderData(allRowsList);
				log.info("salesOrders : " + salesOrders);
				WarehouseApiResponse dbWarehouseApiResponse = new WarehouseApiResponse();
				String authToken = getTransactionAuthToken();
				dbWarehouseApiResponse = transactionService.postSalesOrderV2(salesOrders, authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 *
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param file
	 * @return
	 */
	public Map<String, String> processSalesOrders(String companyCodeId, String plantId, String languageId,
												  String warehouseId, String loginUserId, MultipartFile file) {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if(allRowsList != null && !allRowsList.isEmpty()) {
//				List<SalesOrderV2> salesOrders = prepSalesOrderData(companyCodeId, plantId, languageId, warehouseId, loginUserId, allRowsList);
				List<SalesOrderV2> salesOrders = prepSalesOrderMultipleData(companyCodeId, plantId, languageId, warehouseId, loginUserId, allRowsList);
				log.info("salesOrders : " + salesOrders);
				WarehouseApiResponse dbWarehouseApiResponse = new WarehouseApiResponse();
				String authToken = getTransactionAuthToken();
				dbWarehouseApiResponse = transactionService.postSalesOrderV2(salesOrders, authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 *
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param loginUserId
	 * @param file
	 * @return
	 */
	public Map<String, String> processSalesOrdersV3(String companyCodeId, String plantId, String languageId,
													String warehouseId, String loginUserId, MultipartFile file) {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if(allRowsList != null && !allRowsList.isEmpty()) {
				List<SalesOrderV2> salesOrders = prepSalesOrderDataV3(companyCodeId, plantId, languageId, warehouseId, loginUserId, allRowsList);
				log.info("salesOrders : " + salesOrders);
				WarehouseApiResponse dbWarehouseApiResponse = new WarehouseApiResponse();
				String authToken = getTransactionAuthToken();
				dbWarehouseApiResponse = transactionService.postSalesOrderV2(salesOrders, authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}
	
	/**
	 * -----------------------------Walkaroo changes--------------------------------------------------------------------
	 * @param file
	 * @return
	 */
	public Map<String, String> processDeliveryConfirmationV3 (MultipartFile file) {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if (allRowsList != null && !allRowsList.isEmpty()) {
				DeliveryConfirmationV3 deliveryLines = prepDeliveryConfirmationV3(allRowsList);
				log.info("deliveryLines : " + deliveryLines);
				WarehouseApiResponse dbWarehouseApiResponse = new WarehouseApiResponse();
				String authToken = getTransactionAuthToken();
				dbWarehouseApiResponse = transactionService.postDeliveryConfirmationV3 (deliveryLines, authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 *
	 * @param location
	 * @param file
	 * @return
	 */
	public Map<String, String> storingFile(String location, MultipartFile file) throws Exception {

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);

		String locationPath = null;
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			if (location != null && location.toLowerCase().startsWith("document")) {
				if (location.indexOf('/') > 0) {
					locationPath = propertiesConfig.getDocStorageBasePath() + "/" + location;
				} else {
					// Document template
					locationPath = propertiesConfig.getDocStorageBasePath() + propertiesConfig.getDocStorageDocumentPath();
				}
			}

			log.info("locationPath : " + locationPath);

			if(locationPath != null) {
				this.fileStorageLocation = Paths.get(locationPath).toAbsolutePath().normalize();
				log.info("fileStorageLocation--------> " + fileStorageLocation);

				if (!Files.exists(fileStorageLocation)) {
					try {
						Files.createDirectories(this.fileStorageLocation);
					} catch (Exception ex) {
						ex.printStackTrace();
						throw new BadRequestException("Could not create the directory where the uploaded files will be stored.");
					}
				}

				// Copy file to the target location (Replacing existing file with the same name)
				Path targetLocation = this.fileStorageLocation.resolve(fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			}
			Map<String, String> mapFileProps = new HashMap<>();
			mapFileProps.put("file", fileName);
			mapFileProps.put("location", location);
			mapFileProps.put("status", "UPLOADED");
			return mapFileProps;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	private List<List<String>> readExcelData(File file) {
		try {
			Workbook workbook = new XSSFWorkbook(file);
			workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			List<List<String>> allRowsList = new ArrayList<>();
			DataFormatter fmt = new DataFormatter();
			for (int rn=sheet.getFirstRowNum(); rn<=sheet.getLastRowNum(); rn++) {
				List<String> listUploadData = new ArrayList<>();
				Row row = sheet.getRow(rn);
				log.info("Row:  "+ row.getRowNum());
				if (row == null) {
					// There is no data in this row, handle as needed
				} else if (row.getRowNum() != 0) {
					for (int cn = 0; cn <= row.getLastCellNum(); cn ++) {
						Cell cell = row.getCell(cn);
						if (cell == null) {
							log.info("cell empty: " + cell);
							listUploadData.add("");
						} else {
							String cellStr = fmt.formatCellValue(cell);
							log.info("cellStr: " + cellStr);
							listUploadData.add(cellStr);
						}
					}
					allRowsList.add(listUploadData);
				}
			}

//			Iterator<Row> iterator = sheet.iterator();
//			List<List<String>> allRowsList = new ArrayList<>();
//			while (iterator.hasNext()) {
//				Row currentRow = iterator.next();
//				Iterator<Cell> cellIterator = currentRow.iterator();
//
//				// Moving to data row instead of header row
//				currentRow = iterator.next();
//				cellIterator = currentRow.iterator();
//
//				List<String> listUploadData = new ArrayList<String>();
//				while (cellIterator.hasNext()) {
//					Cell currentCell = cellIterator.next();
//					log.info("===currentCell===== " + currentCell);
//					if (currentCell.getColumnIndex() == 7) {
//						listUploadData.add(" ");
//						log.info("=#= " + listUploadData.size());
//					}
//					if (currentCell.getCellType() == CellType.STRING) {
//						log.info(currentCell.getStringCellValue() + "*****");
//						if (currentCell.getStringCellValue() != null
//								&& !currentCell.getStringCellValue().trim().isEmpty()) {
//							listUploadData.add(currentCell.getStringCellValue());
////							log.info("== " + listUploadData.size());
//						} else {
//							listUploadData.add(" ");
////							log.info("=#= " + listUploadData.size());
//						}
//					} else if (currentCell.getCellType() == CellType.NUMERIC) {
////						log.info(currentCell.getNumericCellValue() + "--");
//						listUploadData.add(String.valueOf(currentCell.getNumericCellValue()));
//					}
//				}
//				log.info("=#= " + listUploadData);
//				allRowsList.add(listUploadData);
//			}
			log.info("list data: " + allRowsList);
			return allRowsList;
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 0 - requiredDeliveryDate
	 * 1 - storeID
	 * 2 - storeName
	 * 3 - transferOrderNumber
	 * 4 - wareHouseId
	 * 5 - lineReference
	 * 6 - orderType
	 * 7 - orderedQty
	 * 8 - sku
	 * 9 - skuDescription
	 * 10 - uom
	 * @param allRowsList
	 * @return
	 */
	private List<ShipmentOrderV2> prepSOData (List<List<String>> allRowsList) {
		List<ShipmentOrderV2> shipmentOrderList = new ArrayList<>();

		for (List<String> listUploadedData : allRowsList) {
			Set<SOHeaderV2> setSOHeader = new HashSet<>();
			List<SOLineV2> soLines = new ArrayList<>();

			// Header
			SOHeaderV2 soHeader = null;
			boolean oneTimeAllow = true;
			for (String column : listUploadedData) {
				if (oneTimeAllow) {
					soHeader = new SOHeaderV2();
					soHeader.setRequiredDeliveryDate(listUploadedData.get(0));
					soHeader.setStoreID(listUploadedData.get(1));
					soHeader.setStoreName(listUploadedData.get(2));
					soHeader.setTransferOrderNumber(listUploadedData.get(3));
					soHeader.setWarehouseId(listUploadedData.get(4));
					setSOHeader.add(soHeader);
				}
				oneTimeAllow = false;

				// Line
				SOLineV2 soLine = new SOLineV2();
				soLine.setLineReference(Long.valueOf(listUploadedData.get(5)));
				soLine.setOrderType(listUploadedData.get(6));
				soLine.setOrderedQty(Double.valueOf(listUploadedData.get(7)));
				soLine.setSku(listUploadedData.get(8));
				soLine.setSkuDescription(listUploadedData.get(9));
				soLine.setUom(listUploadedData.get(10));
				soLines.add(soLine);
			}

			ShipmentOrderV2 shipmentOrder = new ShipmentOrderV2();
			shipmentOrder.setSoHeader(soHeader);
			shipmentOrder.setSoLine(soLines);
			shipmentOrderList.add(shipmentOrder);
		}
		return shipmentOrderList;
	}

	/**
	 *
	 * @param allRowsList
	 * @return
	 */
	private List<SalesOrderV2> prepSalesOrderData (List<List<String>> allRowsList) {
		List<SalesOrderV2> salesOrderList = new ArrayList<>();
		SalesOrderHeaderV2 soHeader = null;
		List<SalesOrderLineV2> soLines = new ArrayList<>();
		boolean oneTimeAllow = true;
		for (List<String> listUploadedData : allRowsList) {
//			Set<SalesOrderHeaderV2> setSOHeader = new HashSet<>();

			// Header

//			for (String column : listUploadedData) {
				if (oneTimeAllow) {
					soHeader = new SalesOrderHeaderV2();
					soHeader.setCompanyCode(listUploadedData.get(0));
					soHeader.setStoreID(listUploadedData.get(1));
					soHeader.setStoreName(listUploadedData.get(2));
					if(listUploadedData.get(3) != null && !listUploadedData.get(3).isBlank()) {
						soHeader.setLanguageId(listUploadedData.get(3));
					}
					soHeader.setWarehouseId(listUploadedData.get(4));
					soHeader.setRequiredDeliveryDate(listUploadedData.get(5));
					soHeader.setPickListNumber(listUploadedData.get(6));
					soHeader.setSalesOrderNumber(listUploadedData.get(7));
					soHeader.setTokenNumber(listUploadedData.get(8));
					soHeader.setOrderType(listUploadedData.get(9));
//					setSOHeader.add(soHeader);
				}
				oneTimeAllow = false;

				// Line
				SalesOrderLineV2 soLine = new SalesOrderLineV2();
				soLine.setOrderType(listUploadedData.get(9));
				soLine.setLineReference(Long.valueOf(listUploadedData.get(10)));
				soLine.setOrderedQty(Double.valueOf(listUploadedData.get(11)));
				soLine.setSku(listUploadedData.get(12));
				soLine.setSkuDescription(listUploadedData.get(13));
				soLine.setUom(listUploadedData.get(14));
				soLine.setStorageSectionId(listUploadedData.get(15));
				soLine.setManufacturerName(listUploadedData.get(16));
				soLine.setManufacturerCode(listUploadedData.get(16));
				soLine.setPickListNo(listUploadedData.get(6));
				soLine.setSalesOrderNo(listUploadedData.get(7));
				soLines.add(soLine);
			}

			SalesOrderV2 salesOrder = new SalesOrderV2();
			salesOrder.setSalesOrderHeader(soHeader);
			salesOrder.setSalesOrderLine(soLines);
			salesOrderList.add(salesOrder);
//		}
		return salesOrderList;
	}

	/**
	 *
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param allRowsList
	 * @return
	 */
	private List<SalesOrderV2> prepSalesOrderData (String companyCodeId, String plantId, String languageId,
												   String warehouseId, String loginUserId, List<List<String>> allRowsList) {
		List<SalesOrderV2> salesOrderList = new ArrayList<>();
		SalesOrderHeaderV2 soHeader = null;
		List<SalesOrderLineV2> soLines = new ArrayList<>();
		boolean oneTimeAllow = true;
		for (List<String> listUploadedData : allRowsList) {
//			Set<SalesOrderHeaderV2> setSOHeader = new HashSet<>();

			// Header

//			for (String column : listUploadedData) {
				if (oneTimeAllow) {
					soHeader = new SalesOrderHeaderV2();
					soHeader.setCompanyCode(companyCodeId);
					soHeader.setStoreID(plantId);
					soHeader.setStoreName(listUploadedData.get(2));
//					if(listUploadedData.get(3) != null && !listUploadedData.get(3).isBlank()) {
						soHeader.setLanguageId(languageId);
//					}
					soHeader.setWarehouseId(warehouseId);
					soHeader.setLoginUserId(loginUserId);
					soHeader.setRequiredDeliveryDate(listUploadedData.get(5));
					soHeader.setPickListNumber(listUploadedData.get(6));
					soHeader.setSalesOrderNumber(listUploadedData.get(7));
					soHeader.setTokenNumber(listUploadedData.get(8));
					soHeader.setOrderType(listUploadedData.get(9));
					if(listUploadedData.get(17) != null && !listUploadedData.get(17).isBlank()) {
						soHeader.setCustomerId(listUploadedData.get(17));
					}
					if(listUploadedData.get(18) != null && !listUploadedData.get(18).isBlank()) {
						soHeader.setCustomerName(listUploadedData.get(18));
					}
//					setSOHeader.add(soHeader);
				}
				oneTimeAllow = false;

				// Line
				SalesOrderLineV2 soLine = new SalesOrderLineV2();
				soLine.setOrderType(listUploadedData.get(9));
				soLine.setLineReference(Long.valueOf(listUploadedData.get(10)));
				soLine.setOrderedQty(Double.valueOf(listUploadedData.get(11)));
				soLine.setSku(listUploadedData.get(12));
				soLine.setSkuDescription(listUploadedData.get(13));
				soLine.setUom(listUploadedData.get(14));
				soLine.setStorageSectionId(listUploadedData.get(15));
				soLine.setManufacturerName(listUploadedData.get(16));
				soLine.setManufacturerCode(listUploadedData.get(16));
				soLine.setPickListNo(listUploadedData.get(6));
				soLine.setSalesOrderNo(listUploadedData.get(7));
				soLines.add(soLine);
			}

			SalesOrderV2 salesOrder = new SalesOrderV2();
			salesOrder.setSalesOrderHeader(soHeader);
			salesOrder.setSalesOrderLine(soLines);
			salesOrderList.add(salesOrder);
//		}
		return salesOrderList;
	}

	/**
	 *
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param loginUserId
	 * @param allRowsList
	 * @return
	 */
	private List<SalesOrderV2> prepSalesOrderDataV3(String companyCodeId, String plantId, String languageId,
													String warehouseId, String loginUserId, List<List<String>> allRowsList) {
		List<SalesOrderV2> salesOrderList = new ArrayList<>();
		SalesOrderHeaderV2 soHeader = null;
		List<SalesOrderLineV2> soLines = new ArrayList<>();
		boolean oneTimeAllow = true;
		boolean isSameOrder = true;
		String orderNumber = null;
		int i = 1;
		String orderGroupByUpload = String.valueOf(System.currentTimeMillis());
		for (List<String> listUploadedData : allRowsList) {
			if (orderNumber != null) {
				isSameOrder = orderNumber.equalsIgnoreCase(listUploadedData.get(0));
			}
			if (!isSameOrder) {
				SalesOrderV2 salesOrder = new SalesOrderV2();
				salesOrder.setSalesOrderHeader(soHeader);
				salesOrder.setSalesOrderLine(soLines);
				salesOrderList.add(salesOrder);

				//reset to create new order
				oneTimeAllow = true;
				isSameOrder = true;
				orderNumber = null;
				soLines = new ArrayList<>();
			}
			if (isSameOrder) {
				orderNumber = listUploadedData.get(0);
				// Header
				if (oneTimeAllow) {
					soHeader = new SalesOrderHeaderV2();
					soHeader.setCompanyCode(companyCodeId);
					soHeader.setStoreID(plantId);
					soHeader.setLanguageId(languageId);
					soHeader.setWarehouseId(warehouseId);
					soHeader.setLoginUserId(loginUserId);
					soHeader.setPickListNumber(listUploadedData.get(0));
					soHeader.setCustomerId(listUploadedData.get(2));
					soHeader.setCustomerName(listUploadedData.get(3));
					soHeader.setRequiredDeliveryDate(listUploadedData.get(12));
					if (listUploadedData.size() > 15 && listUploadedData.get(15) != null && !listUploadedData.get(15).isBlank()) {
						soHeader.setOrderType(listUploadedData.get(15));
					} else {
						soHeader.setOrderType("3");
					}
					soHeader.setSalesOrderNumber(orderGroupByUpload);
				}
				oneTimeAllow = false;

				// Line
				SalesOrderLineV2 soLine = new SalesOrderLineV2();
				if (listUploadedData.size() > 15 && listUploadedData.get(15) != null && !listUploadedData.get(15).isBlank()) {
					soLine.setOrderType(listUploadedData.get(9));
				} else {
					soLine.setOrderType("3");
				}
				soLine.setLineReference(Long.valueOf(listUploadedData.get(1)));
				soLine.setOrderedQty(Double.valueOf(listUploadedData.get(13)));
				soLine.setSku(listUploadedData.get(4));
				if (listUploadedData.size() > 14 && listUploadedData.get(14) != null && !listUploadedData.get(14).isBlank()) {
					soLine.setUom(listUploadedData.get(14));
				} else {
					soLine.setUom(UOM);
				}
				soLine.setPickListNo(listUploadedData.get(0));
				soLine.setSalesOrderNo(orderGroupByUpload);
				soLine.setMaterialNo(listUploadedData.get(5));
				soLine.setPriceSegment(listUploadedData.get(6));
				soLine.setArticleNo(listUploadedData.get(7));
				soLine.setGender(listUploadedData.get(8));
				soLine.setColor(listUploadedData.get(9));
				soLine.setSize(listUploadedData.get(10));
				soLine.setNoPairs(listUploadedData.get(11));
				soLines.add(soLine);
			}

			if (allRowsList.size() == i) {
				SalesOrderV2 salesOrder = new SalesOrderV2();
				salesOrder.setSalesOrderHeader(soHeader);
				salesOrder.setSalesOrderLine(soLines);
				salesOrderList.add(salesOrder);
			}
			i++;
		}

		return salesOrderList;
	}
	
	/**
	 * --------------------------Walkaroo changes----------------------------------------------------------------------
	 * @param allRowsList
	 * @return
	 */
	private DeliveryConfirmationV3 prepDeliveryConfirmationV3 (List<List<String>> allRowsList) {
		DeliveryConfirmationV3 deliveryConfirmationV3 = new DeliveryConfirmationV3();
		List<DeliveryConfirmationLineV3> deliveryLines = new ArrayList<>();
		for (List<String> listUploadedData : allRowsList) {
			DeliveryConfirmationLineV3 line = new DeliveryConfirmationLineV3();
			line.setOutbound(listUploadedData.get(0));
			line.setCustomerCode(listUploadedData.get(1));
			line.setCustomer(listUploadedData.get(2));
			line.setSkuCode(listUploadedData.get(3));
			line.setMaterial(listUploadedData.get(4));
			line.setPriceSegement(listUploadedData.get(5));
			line.setArticleNumber(listUploadedData.get(6));
			line.setGender(listUploadedData.get(7));
			line.setColor(listUploadedData.get(8));
			line.setSize(listUploadedData.get(9));
			line.setNoOfPairs(listUploadedData.get(10));
			line.setHuSerialNo(listUploadedData.get(11));
			line.setPickedQty(listUploadedData.get(12));
			deliveryLines.add(line);
		}
		deliveryConfirmationV3.setLines(deliveryLines);
		return deliveryConfirmationV3;
	}

	/**
	 * loadFileAsResource
	 *
	 * @param fileName
	 * @return
	 */
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new BadRequestException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new BadRequestException("File not found " + fileName);
		}
	}

//================================================================================================================
	/**
	 *
	 * @param file
	 * @return
	 */
	public Map<String, String> processAsnOrders(MultipartFile file) throws Exception {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if(allRowsList != null && !allRowsList.isEmpty()) {
				List<ASNV2> asnV2Orders = prepAsnData(allRowsList);
				log.info("asnOrders : " + asnV2Orders);

				// Uploading Orders
				WarehouseApiResponse[] dbWarehouseApiResponse = new WarehouseApiResponse[0];
				String authToken = getTransactionAuthToken();
				dbWarehouseApiResponse = transactionService.postASNV2Upload(asnV2Orders, "Uploaded", authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 *
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param file
	 * @return
	 */
	public Map<String, String> processAsnOrders(String companyCodeId, String plantId, String languageId,
												String warehouseId, String loginUserId, MultipartFile file) {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if(allRowsList != null && !allRowsList.isEmpty()) {
//				List<ASNV2> asnV2Orders = prepAsnData(companyCodeId, plantId, languageId, warehouseId, loginUserId, allRowsList);
				List<ASNV2> asnV2Orders = prepAsnMultipleData(companyCodeId, plantId, languageId, warehouseId, loginUserId, allRowsList);
				log.info("asnOrders : " + asnV2Orders);

				// Uploading Orders
				WarehouseApiResponse[] dbWarehouseApiResponse = new WarehouseApiResponse[0];
//				String authToken = getTransactionAuthToken();
//				dbWarehouseApiResponse = transactionService.postASNV2Upload(asnV2Orders, loginUserId, authToken);
				String authToken = getEnterpriseAuthToken();
				dbWarehouseApiResponse = enterpriseSetupService.postASNV2Upload(asnV2Orders, loginUserId, authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}
	
	/**
	 * Walkaroo
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param file
	 * @return
	 */
	public Map<String, String> processAsnOrdersV3(String companyCodeId, String plantId, String languageId,
												  String warehouseId, String loginUserId, MultipartFile file) {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if(allRowsList != null && !allRowsList.isEmpty()) {
				List<ASNV2> asnV2Orders = prepAsnMultipleDataV3(companyCodeId, plantId, languageId, warehouseId, loginUserId, allRowsList);
				log.info("asnOrders : " + asnV2Orders);

				// Uploading Orders
				WarehouseApiResponse[] dbWarehouseApiResponse = new WarehouseApiResponse[0];
				String authToken = getTransactionAuthToken();
				dbWarehouseApiResponse = transactionService.postASNV2Upload(asnV2Orders, "Uploaded", authToken);

			if(dbWarehouseApiResponse != null) {
				Map<String, String> mapFileProps = new HashMap<>();
				mapFileProps.put("file", fileName);
				mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
				return mapFileProps;
			}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	public Map<String, String> processInterWarehouseTransferInOrders(MultipartFile file) throws Exception {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if (allRowsList != null && !allRowsList.isEmpty()) {
				List<InterWarehouseTransferInV2> wh2whOrders = prepInterwareHouseInData(allRowsList);
				log.info("wh2whOrders : " + wh2whOrders);

				// Uploading Orders
				WarehouseApiResponse[] dbWarehouseApiResponse = new WarehouseApiResponse[0];
				String authToken = getTransactionAuthToken();
				dbWarehouseApiResponse = transactionService.postInterWarehouseTransferInUploadV2(wh2whOrders, "Uploaded", authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 *
	 * @param allRowsList
	 * @return
	 */
	private List<ASNV2> prepAsnData (List<List<String>> allRowsList) {
		List<ASNV2> orderList = new ArrayList<>();
		boolean oneTimeAllow = true;
		ASNHeaderV2 header = null;
		List<ASNLineV2> lisAsnLine = new ArrayList<>();
		for (List<String> listUploadedData : allRowsList) {
//			Set<ASNHeaderV2> setWHHeader = new HashSet<>();
//			List<ASNLineV2> lisAsnLine = new ArrayList<>();

			// Header
//			ASNHeaderV2 header = null;
//			boolean oneTimeAllow = true;
//			for (String column : listUploadedData) {
				if (oneTimeAllow) {
					header = new ASNHeaderV2();
					/*
					 * branchCode
					 * companyCode
					 * asnNumber
					 */
					header.setBranchCode(listUploadedData.get(0));
					header.setCompanyCode(listUploadedData.get(1));
					header.setAsnNumber(listUploadedData.get(2));
					if(listUploadedData.get(18) != null && !listUploadedData.get(18).isBlank()) {
						header.setInboundOrderTypeId(Long.valueOf(listUploadedData.get(18)));
					}
//					setWHHeader.add(header);
				}
				oneTimeAllow = false;

				/*
				 * lineReference
				 * sku
				 * skuDescription
				 * containerNumber
				 * supplierCode
				 * supplierPartNumber
				 * manufacturerName
				 * manufacturerCode
				 * expectedDate
				 * expectedQty
				 * uom
				 * origin
				 * supplierName
				 * Brand
				 *packQty
				 */
				// Line
				ASNLineV2 line = new ASNLineV2();
				line.setLineReference(Long.valueOf(listUploadedData.get(3)));
				line.setSku(listUploadedData.get(4));
				line.setSkuDescription(listUploadedData.get(5));
				line.setContainerNumber(listUploadedData.get(6));
				line.setSupplierCode(listUploadedData.get(7));
				line.setSupplierPartNumber(listUploadedData.get(8));
				line.setManufacturerName(listUploadedData.get(9));
				line.setManufacturerCode(listUploadedData.get(10));
				line.setExpectedDate(listUploadedData.get(11));
				line.setExpectedQty(Double.valueOf(listUploadedData.get(12)));
				line.setUom(listUploadedData.get(13));
				line.setOrigin(listUploadedData.get(14));
				line.setSupplierName(listUploadedData.get(15));
				line.setBrand(listUploadedData.get(16));

				if (!listUploadedData.get(17).trim().isEmpty()) {
					line.setPackQty(Double.valueOf(listUploadedData.get(17)));
				}
				if(listUploadedData.get(18) != null && !listUploadedData.get(18).isBlank()) {
					line.setInboundOrderTypeId(Long.valueOf(listUploadedData.get(18)));
				}
				if(listUploadedData.get(19) != null && !listUploadedData.get(19).isBlank()) {
					line.setBatchSerialNumber(listUploadedData.get(19));
				}

				lisAsnLine.add(line);
//			}

//			ASNV2 orders = new ASNV2();
//			orders.setAsnHeader(header);
//			orders.setAsnLine(lisAsnLine);
//			orderList.add(orders);
		}
		ASNV2 orders = new ASNV2();
		orders.setAsnHeader(header);
		orders.setAsnLine(lisAsnLine);
		orderList.add(orders);
		return orderList;
	}

	/**
	 *
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param allRowsList
	 * @return
	 */
	private List<ASNV2> prepAsnData (String companyCodeId, String plantId,
									 String languageId, String warehouseId, String loginUserId,
									 List<List<String>> allRowsList) {
		List<ASNV2> orderList = new ArrayList<>();
		boolean oneTimeAllow = true;
		ASNHeaderV2 header = null;
		List<ASNLineV2> lisAsnLine = new ArrayList<>();
		for (List<String> listUploadedData : allRowsList) {
//			Set<ASNHeaderV2> setWHHeader = new HashSet<>();
//			List<ASNLineV2> lisAsnLine = new ArrayList<>();

			// Header
//			ASNHeaderV2 header = null;
//			boolean oneTimeAllow = true;
//			for (String column : listUploadedData) {
				if (oneTimeAllow) {
					header = new ASNHeaderV2();
					/*
					 * branchCode
					 * companyCode
					 * asnNumber
					 */
					header.setBranchCode(plantId);
					header.setCompanyCode(companyCodeId);
					header.setLanguageId(languageId);
					header.setWarehouseId(warehouseId);
					header.setLoginUserId(loginUserId);
					header.setAsnNumber(listUploadedData.get(2));
					if(listUploadedData.get(18) != null && !listUploadedData.get(18).isBlank()) {
						header.setInboundOrderTypeId(Long.valueOf(listUploadedData.get(18)));
					}
//					setWHHeader.add(header);
				}
				oneTimeAllow = false;

				/*
				 * lineReference
				 * sku
				 * skuDescription
				 * containerNumber
				 * supplierCode
				 * supplierPartNumber
				 * manufacturerName
				 * manufacturerCode
				 * expectedDate
				 * expectedQty
				 * uom
				 * origin
				 * supplierName
				 * Brand
				 *packQty
				 */
				// Line
				ASNLineV2 line = new ASNLineV2();
				line.setLineReference(Long.valueOf(listUploadedData.get(3)));
				line.setSku(listUploadedData.get(4));
				line.setSkuDescription(listUploadedData.get(5));
				line.setContainerNumber(listUploadedData.get(6));
				line.setSupplierCode(listUploadedData.get(7));
				line.setSupplierPartNumber(listUploadedData.get(8));
				line.setManufacturerName(listUploadedData.get(9));
				line.setManufacturerCode(listUploadedData.get(10));
				line.setExpectedDate(listUploadedData.get(11));
				line.setExpectedQty(Double.valueOf(listUploadedData.get(12)));
				line.setUom(listUploadedData.get(13));
				line.setOrigin(listUploadedData.get(14));
				line.setSupplierName(listUploadedData.get(15));
				line.setBrand(listUploadedData.get(16));

				if (!listUploadedData.get(17).trim().isEmpty()) {
					line.setPackQty(Double.valueOf(listUploadedData.get(17)));
				}
				if(listUploadedData.get(18) != null && !listUploadedData.get(18).isBlank()) {
					line.setInboundOrderTypeId(Long.valueOf(listUploadedData.get(18)));
				}
				if(listUploadedData.get(19) != null && !listUploadedData.get(19).isBlank()) {
					line.setBatchSerialNumber(listUploadedData.get(19));
				}

				lisAsnLine.add(line);
//			}

//			ASNV2 orders = new ASNV2();
//			orders.setAsnHeader(header);
//			orders.setAsnLine(lisAsnLine);
//			orderList.add(orders);
		}
		ASNV2 orders = new ASNV2();
		orders.setAsnHeader(header);
		orders.setAsnLine(lisAsnLine);
		orderList.add(orders);
		return orderList;
	}

	/**
	 *
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param loginUserId
	 * @param allRowsList
	 * @return
	 */
	private List<ASNV2> prepAsnMultipleDataV3(String companyCodeId, String plantId,
											  String languageId, String warehouseId, String loginUserId,
											  List<List<String>> allRowsList) {
		List<ASNV2> orderList = new ArrayList<>();
		String orderNumber = null;
		boolean oneTimeAllow = true;
		boolean isSameOrder = true;
		int i = 1;
		ASNHeaderV2 header = null;
		List<ASNLineV2> lisAsnLine = new ArrayList<>();
		for (List<String> listUploadedData : allRowsList) {
			if(orderNumber != null) {
				isSameOrder = orderNumber.equalsIgnoreCase(listUploadedData.get(0));
			}

			if(!isSameOrder) {
				ASNV2 orders = new ASNV2();
				orders.setAsnHeader(header);
				orders.setAsnLine(lisAsnLine);
				orderList.add(orders);

				//reset to create new order
				oneTimeAllow = true;
				isSameOrder = true;
				orderNumber = null;
				lisAsnLine = new ArrayList<>();
			}

			if(isSameOrder) {
				orderNumber = listUploadedData.get(0);
				if (oneTimeAllow) {
					header = new ASNHeaderV2();

					header.setBranchCode(plantId);
					header.setCompanyCode(companyCodeId);
					header.setLanguageId(languageId);
					header.setWarehouseId(warehouseId);
					header.setLoginUserId(loginUserId);
					header.setAsnNumber(listUploadedData.get(0));
					if (listUploadedData.size() > 15 && listUploadedData.get(15) != null && !listUploadedData.get(15).isBlank()) {
						header.setInboundOrderTypeId(Long.valueOf(listUploadedData.get(15)));
					} else {
						header.setInboundOrderTypeId(1L);
					}
				}
				oneTimeAllow = false;

				// Line
				ASNLineV2 line = new ASNLineV2();
				line.setLineReference(Long.valueOf(listUploadedData.get(1)));
				line.setSku(listUploadedData.get(2));
				line.setBarcodeId(listUploadedData.get(3));
				line.setMaterialNo(listUploadedData.get(4));
				line.setPriceSegment(listUploadedData.get(5));
				line.setArticleNo(listUploadedData.get(6));
				line.setGender(listUploadedData.get(7));
				line.setColor(listUploadedData.get(8));
				line.setSize(listUploadedData.get(9));
				line.setNoPairs(listUploadedData.get(10));
				line.setSupplierCode(listUploadedData.get(11));
				line.setExpectedDate(listUploadedData.get(12));
				line.setExpectedQty(Double.valueOf(listUploadedData.get(13)));

				line.setBranchCode(plantId);
				line.setCompanyCode(companyCodeId);

				if (listUploadedData.size() > 14 && !listUploadedData.get(14).trim().isEmpty()) {
					line.setUom(listUploadedData.get(14));
				} else {
					line.setUom(UOM);
				}
				if (listUploadedData.size() > 15 && listUploadedData.get(15) != null && !listUploadedData.get(15).isBlank()) {
					line.setInboundOrderTypeId(Long.valueOf(listUploadedData.get(15)));
				} else {
					header.setInboundOrderTypeId(1L);
				}

				lisAsnLine.add(line);
			}
			if(allRowsList.size() == i) {
				ASNV2 orders = new ASNV2();
				orders.setAsnHeader(header);
				orders.setAsnLine(lisAsnLine);
				orderList.add(orders);
			}
			i++;
		}
		return orderList;
	}


	/**
	 *
	 * @param allRowsList
	 * @return
	 */
	private List<InterWarehouseTransferInV2> prepInterwareHouseInData (List<List<String>> allRowsList) {
		List<InterWarehouseTransferInV2> whOrderList = new ArrayList<>();
		for (List<String> listUploadedData : allRowsList) {
			Set<InterWarehouseTransferInHeaderV2> setWHHeader = new HashSet<>();
			List<InterWarehouseTransferInLineV2> listWHLines = new ArrayList<>();

			// Header
			InterWarehouseTransferInHeaderV2 header = null;
			boolean oneTimeAllow = true;
			for (String column : listUploadedData) {
				if (oneTimeAllow) {
					header = new InterWarehouseTransferInHeaderV2();
					/*
					 * transferOrderNumber
					 * toCompanyCode
					 * toBranchCode
					 */
					header.setTransferOrderNumber(listUploadedData.get(0));
					header.setToCompanyCode(listUploadedData.get(1));
					header.setToBranchCode(listUploadedData.get(2));
					setWHHeader.add(header);
				}
				oneTimeAllow = false;

				/*
				 * fromCompanyCode
				 * origin
				 * supplierName
				 * manufacturerCode
				 * Brand
				 * fromBranchCode
				 * lineReference
				 * sku
				 * skuDescription
				 * supplierPartNumber
				 * manufacturerName
				 * expectedDate
				 * expectedQty
				 * uom
				 * packQty
				 */
				// Line
				InterWarehouseTransferInLineV2 line = new InterWarehouseTransferInLineV2();
				line.setFromCompanyCode(listUploadedData.get(3));
				line.setOrigin(listUploadedData.get(4));
				line.setSupplierName(listUploadedData.get(5));
				line.setManufacturerCode(listUploadedData.get(6));
				line.setBrand(listUploadedData.get(7));
				line.setFromBranchCode(listUploadedData.get(8));
				line.setLineReference(Long.valueOf(listUploadedData.get(9)));
				line.setSku(listUploadedData.get(10));
				line.setSkuDescription(listUploadedData.get(11));
				line.setSupplierPartNumber(listUploadedData.get(12));
				line.setManufacturerName(listUploadedData.get(13));
				line.setExpectedDate(listUploadedData.get(14));
				line.setExpectedQty(Double.valueOf(listUploadedData.get(15)));
				line.setUom(listUploadedData.get(16));

				if (!listUploadedData.get(17).trim().isEmpty()) {
					line.setPackQty(Double.valueOf(listUploadedData.get(17)));
				}

				listWHLines.add(line);
			}

			InterWarehouseTransferInV2 whOrder = new InterWarehouseTransferInV2();
			whOrder.setInterWarehouseTransferInHeader(header);
			whOrder.setInterWarehouseTransferInLine(listWHLines);
			whOrderList.add(whOrder);
		}
		return whOrderList;
	}

	/**
	 *
	 * @param file
	 * @return
	 */
	public Map<String, String> processBinToBin(MultipartFile file) throws Exception {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if (allRowsList != null && !allRowsList.isEmpty()) {
			List<InhouseTransferUpload> inhouseTransferUploads = prepInHouseTransferHeaderV2(allRowsList);
			log.info("inhouseTransferUploads bin-to-bin : " + inhouseTransferUploads);

			// Uploading Orders
			String authToken = getTransactionAuthToken();
			WarehouseApiResponse dbWarehouseApiResponse = transactionService.createInhouseTransferUploadV2(inhouseTransferUploads, "UP_AMS", authToken);

			if (dbWarehouseApiResponse != null) {
				Map<String, String> mapFileProps = new HashMap<>();
				mapFileProps.put("file", fileName);
				mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
				return mapFileProps;
			}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 * @param allRowsList
	 * @return
	 */
	private List<InhouseTransferUpload> prepInHouseTransferHeaderV2(List<List<String>> allRowsList) {
		List<InhouseTransferUpload> orderList = new ArrayList<>();
		for (List<String> listUploadedData : allRowsList) {
			Set<InhouseTransferHeader> setInhouseTransferHeader = new HashSet<>();
			List<InhouseTransferLine> listInhouseTransferLine = new ArrayList<>();

			// Header
			InhouseTransferHeader header = null;
			boolean oneTimeAllow = true;

			if (oneTimeAllow) {
				header = new InhouseTransferHeader();
				/*
				 * companyCodeId
				 * plantId
				 * languageId
				 * warehouseId
				 * TransferTypeId
				 */
				header.setCompanyCodeId(listUploadedData.get(0));
				header.setPlantId(listUploadedData.get(1));
				header.setLanguageId(listUploadedData.get(2));
				header.setWarehouseId(listUploadedData.get(3));
				header.setTransferMethod("ONESTEP");
				if (listUploadedData.get(4) != null) {
					header.setTransferTypeId(Long.valueOf(listUploadedData.get(4)));
				} else {
					header.setTransferTypeId(3L);
				}

				setInhouseTransferHeader.add(header);
			}
			oneTimeAllow = false;

			/*
			 * itemCode
			 * manufacturerName
			 * sourceStorageBin
			 * targetStorageBin
			 * transferOrderQty
			 * transferConfirmQty
			 * transferUOM
			 * stockTypeId
			 * specialStockIndicatorId
			 * palletcode
			 * casecode
			 * packbarcode
			 */
			// Line
			InhouseTransferLine line = new InhouseTransferLine();
			line.setCompanyCodeId(listUploadedData.get(0));
			line.setPlantId(listUploadedData.get(1));
			line.setLanguageId(listUploadedData.get(2));
			line.setWarehouseId(listUploadedData.get(3));
			line.setSourceItemCode(listUploadedData.get(5));
			line.setTargetItemCode(listUploadedData.get(5));
			line.setManufacturerName(listUploadedData.get(6));
			if (listUploadedData.get(7).equalsIgnoreCase(listUploadedData.get(8))) {
				throw new BadRequestException("Source and Target Storage Bin cannot be same");
			}
			line.setSourceStorageBin(listUploadedData.get(7));
			line.setTargetStorageBin(listUploadedData.get(8));
			if (listUploadedData.get(9) == null) {
				throw new BadRequestException("Transfer Qty must not be null");
			}
			if (Double.valueOf(listUploadedData.get(9)) <= 0D) {
				throw new BadRequestException("Transfer Qty must be greater than zero");
			}
			if (!listUploadedData.get(9).trim().isEmpty()) {
				line.setTransferOrderQty(Double.valueOf(listUploadedData.get(9)));
				line.setTransferConfirmedQty(Double.valueOf(listUploadedData.get(9)));
			}
			line.setTransferUom(listUploadedData.get(10));
			line.setSourceStockTypeId(Long.valueOf(listUploadedData.get(11)));
			line.setTargetStockTypeId(Long.valueOf(listUploadedData.get(11)));
			line.setSpecialStockIndicatorId(Long.valueOf(listUploadedData.get(12)));
			line.setPalletCode(listUploadedData.get(13));
			line.setCaseCode(listUploadedData.get(14));
			line.setPackBarcodes(listUploadedData.get(15));

			listInhouseTransferLine.add(line);

			InhouseTransferUpload inhouseTransferUpload = new InhouseTransferUpload();
			inhouseTransferUpload.setInhouseTransferHeader(header);
			inhouseTransferUpload.setInhouseTransferLine(listInhouseTransferLine);
			orderList.add(inhouseTransferUpload);
		}
		return orderList;
	}

	/**
	 * @param file
	 * @return
	 */
	public Map<String, String> processStockAdjustment(MultipartFile file) throws Exception {
		this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("loca : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);

			List<List<String>> allRowsList = readExcelData(targetLocation.toFile());
			if(allRowsList != null && !allRowsList.isEmpty()) {
				List<StockAdjustment> stockAdjustmentList = prepStockAdjustment(allRowsList);
				log.info("StockAdjustment List: " + stockAdjustmentList);

				// Uploading Orders
				String authToken = getTransactionAuthToken();
				WarehouseApiResponse dbWarehouseApiResponse = transactionService.createStockAdjustmentUploadV2(stockAdjustmentList, authToken);

				if (dbWarehouseApiResponse != null) {
					Map<String, String> mapFileProps = new HashMap<>();
					mapFileProps.put("file", fileName);
					mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
					return mapFileProps;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}
		return null;
	}

	/**
	 * @param file
	 * @param filePath
	 * @return
	 */
	public Map<String, String> storeSingleFile(MultipartFile file, String filePath) {

		if (!filePath.startsWith("/")) {
			filePath = "/" + filePath;
		}

		this.fileStorageLocation = Paths.get(propertiesConfig.getDocStorageBasePath() + filePath).toAbsolutePath().normalize();
		if (!Files.exists(fileStorageLocation)) {
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception ex) {
				throw new BadRequestException(
						"Could not create the directory where the uploaded files will be stored.");
			}
		}

		log.info("location : " + fileStorageLocation);

		// Normalize file name
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		log.info("filename before: " + fileName);
		fileName = fileName.replace(" ", "_");
		log.info("filename after: " + fileName);
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			log.info("Copied : " + targetLocation);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
		}

		Map<String, String> mapFileProps = new HashMap<>();
		mapFileProps.put("file", fileName);
		mapFileProps.put("location", filePath);
		mapFileProps.put("status", "UPLOADED");
		return mapFileProps;
	}

	/**
	 * @param location
	 * @param file
	 * @return
	 */
	public String getQualifiedFilePath(String location, String file) {
		String filePath = propertiesConfig.getDocStorageBasePath();

		log.info("getQualifiedFilePath---location------>: " + location);
		log.info("getQualifiedFilePath---file------>: " + file);

		if (location.startsWith("/")) {
			filePath = filePath + location;
		} else {
			filePath = filePath + "/" + location;
		}

		if (location.endsWith("/")) {
			filePath = filePath + file;
		} else {
			filePath = filePath + "/" + file;
		}
		log.info("filePath: " + filePath);
		return filePath;
	}

	/**
	 * @param allRowsList
	 * @return
	 */
	private List<StockAdjustment> prepStockAdjustment(List<List<String>> allRowsList) {
		List<StockAdjustment> orderList = new ArrayList<>();
		for (List<String> listUploadedData : allRowsList) {

			/*
			 * companyCodeId
			 * plantId
			 * warehouseId
			 * date of adjustment
			 * is cycle count
			 * is damage
			 * itemCode
			 * itemDescription
			 * manufacturerName
			 * ManufacturerCode
			 * UOM
			 * adjustmentQty
			 */
			StockAdjustment header = new StockAdjustment();
			header.setCompanyCode(listUploadedData.get(0));
			header.setBranchCode(listUploadedData.get(1));
			header.setWarehouseId(listUploadedData.get(2));
			header.setDateOfAdjustment(new Date());
			header.setIsCycleCount(listUploadedData.get(3));
			header.setIsDamage(listUploadedData.get(4));
			header.setItemCode(listUploadedData.get(5));
			header.setItemDescription(listUploadedData.get(6));
			header.setManufacturerName(listUploadedData.get(7));
			header.setManufacturerCode(listUploadedData.get(8));
			header.setUnitOfMeasure(listUploadedData.get(9));
			if (listUploadedData.get(10) != null) {
				header.setAdjustmentQty(Double.valueOf(listUploadedData.get(10)));
			}
			orderList.add(header);
		}
		return orderList;
	}

	/**
	 * Upload Multiple Order - ASN
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param loginUserId
	 * @param allRowsList
	 * @return
	 */
	private List<ASNV2> prepAsnMultipleData(String companyCodeId, String plantId,
											String languageId, String warehouseId, String loginUserId,
											List<List<String>> allRowsList) {
		List<ASNV2> orderList = new ArrayList<>();
		String orderNumber = null;
		boolean oneTimeAllow = true;
		boolean isSameOrder = true;
		int i = 1;
		ASNHeaderV2 header = null;
		List<ASNLineV2> lisAsnLine = new ArrayList<>();
		for (List<String> listUploadedData : allRowsList) {
			if (orderNumber != null) {
				isSameOrder = orderNumber.equalsIgnoreCase(listUploadedData.get(0));
			}

			if (!isSameOrder) {
				ASNV2 orders = new ASNV2();
				orders.setAsnHeader(header);
				orders.setAsnLine(lisAsnLine);
				orderList.add(orders);

				//reset to create new order
				oneTimeAllow = true;
				isSameOrder = true;
				orderNumber = null;
				lisAsnLine = new ArrayList<>();
			}

			if (isSameOrder) {
				orderNumber = listUploadedData.get(0);
				if (oneTimeAllow) {
					header = new ASNHeaderV2();

					header.setBranchCode(plantId);
					header.setCompanyCode(companyCodeId);
					header.setLanguageId(languageId);
					header.setWarehouseId(warehouseId);
					header.setLoginUserId(loginUserId);
					header.setAsnNumber(listUploadedData.get(0));
					header.setSupplierCode(listUploadedData.get(7));
					if (listUploadedData.size() > 18 && listUploadedData.get(18) != null && !listUploadedData.get(18).isBlank()) {
						header.setInboundOrderTypeId(Long.valueOf(listUploadedData.get(18)));
					} else {
						header.setInboundOrderTypeId(1L);
					}
				}
				oneTimeAllow = false;

				// Line
				ASNLineV2 line = new ASNLineV2();
				line.setLineReference(Long.valueOf(listUploadedData.get(1)));
				line.setSku(listUploadedData.get(2));
				line.setBarcodeId(listUploadedData.get(3));
				line.setSkuDescription(listUploadedData.get(4));
				line.setBatchSerialNumber(listUploadedData.get(5));
				line.setContainerNumber(listUploadedData.get(6));
				line.setSupplierCode(listUploadedData.get(7));
				line.setSupplierName(listUploadedData.get(8));
				line.setSupplierPartNumber(listUploadedData.get(9));
				line.setManufacturerName(listUploadedData.get(10));
				line.setManufacturerCode(listUploadedData.get(11));
				line.setExpectedDate(listUploadedData.get(12));
				line.setExpectedQty(Double.valueOf(listUploadedData.get(13)));

				line.setBranchCode(plantId);
				line.setCompanyCode(companyCodeId);

				if (listUploadedData.size() > 14 && !listUploadedData.get(14).trim().isEmpty()) {
					line.setUom(listUploadedData.get(14));
				} else {
					line.setUom(UOM);
				}
				if (listUploadedData.size() > 15 && !listUploadedData.get(15).trim().isEmpty()) {
					line.setOrigin(listUploadedData.get(15));
				}
				if (listUploadedData.size() > 16 && !listUploadedData.get(16).trim().isEmpty()) {
					line.setBrand(listUploadedData.get(16));
				}
				if (listUploadedData.size() > 17 && listUploadedData.get(17) != null && !listUploadedData.get(17).trim().isEmpty()) {
					line.setPackQty(Double.valueOf(listUploadedData.get(17)));
				}
				if (listUploadedData.size() > 18 && listUploadedData.get(18) != null && !listUploadedData.get(18).isBlank()) {
					line.setInboundOrderTypeId(Long.valueOf(listUploadedData.get(18)));
				} else {
					header.setInboundOrderTypeId(1L);
				}

				lisAsnLine.add(line);
			}
			if (allRowsList.size() == i) {
				ASNV2 orders = new ASNV2();
				orders.setAsnHeader(header);
				orders.setAsnLine(lisAsnLine);
				orderList.add(orders);
			}
			i++;
		}
		return orderList;
	}

	/**
	 * Upload Multiple Order - PickList / SalesOrder
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param loginUserId
	 * @param allRowsList
	 * @return
	 */
	private List<SalesOrderV2> prepSalesOrderMultipleData(String companyCodeId, String plantId, String languageId,
														  String warehouseId, String loginUserId, List<List<String>> allRowsList) {
		List<SalesOrderV2> salesOrderList = new ArrayList<>();
		SalesOrderHeaderV2 soHeader = null;
		List<SalesOrderLineV2> soLines = new ArrayList<>();
		boolean oneTimeAllow = true;
		boolean isSameOrder = true;
		String orderNumber = null;
		int i = 1;
		for (List<String> listUploadedData : allRowsList) {
			if (orderNumber != null) {
				isSameOrder = orderNumber.equalsIgnoreCase(listUploadedData.get(0));
			}
			if (!isSameOrder) {
				SalesOrderV2 salesOrder = new SalesOrderV2();
				salesOrder.setSalesOrderHeader(soHeader);
				salesOrder.setSalesOrderLine(soLines);
				salesOrderList.add(salesOrder);

				//reset to create new order
				oneTimeAllow = true;
				isSameOrder = true;
				orderNumber = null;
				soLines = new ArrayList<>();
			}
			if (isSameOrder) {
				orderNumber = listUploadedData.get(0);
				// Header
				if (oneTimeAllow) {
					soHeader = new SalesOrderHeaderV2();
					soHeader.setCompanyCode(companyCodeId);
					soHeader.setStoreID(plantId);
					soHeader.setLanguageId(languageId);
					soHeader.setWarehouseId(warehouseId);
					soHeader.setLoginUserId(loginUserId);
					soHeader.setPickListNumber(listUploadedData.get(0));
					soHeader.setSalesOrderNumber(listUploadedData.get(1));
					soHeader.setCustomerId(listUploadedData.get(2));
					soHeader.setCustomerName(listUploadedData.get(3));
					soHeader.setRequiredDeliveryDate(listUploadedData.get(9));
					if (listUploadedData.size() > 13 && listUploadedData.get(13) != null && !listUploadedData.get(13).isBlank()) {
						soHeader.setTokenNumber(listUploadedData.get(13));
					}
					if (listUploadedData.size() > 14 && listUploadedData.get(14) != null && !listUploadedData.get(14).isBlank()) {
						soHeader.setOrderType(listUploadedData.get(14));
					} else {
						soHeader.setOrderType("3");
					}
				}
				oneTimeAllow = false;

				// Line
				SalesOrderLineV2 soLine = new SalesOrderLineV2();
				if (listUploadedData.size() > 14 && listUploadedData.get(14) != null && !listUploadedData.get(14).isBlank()) {
					soLine.setOrderType(listUploadedData.get(14));
				} else {
					soLine.setOrderType("3");
				}
				soLine.setLineReference(Long.valueOf(listUploadedData.get(4)));
				soLine.setBarcodeId(listUploadedData.get(5));
				soLine.setSku(listUploadedData.get(6));
				soLine.setSkuDescription(listUploadedData.get(7));
				soLine.setOrderedQty(Double.valueOf(listUploadedData.get(8)));
				if (listUploadedData.size() > 10 && listUploadedData.get(10) != null && !listUploadedData.get(10).isBlank()) {
					soLine.setUom(listUploadedData.get(10));
				} else {
					soLine.setUom(UOM);
				}
				if (listUploadedData.size() > 11 && listUploadedData.get(11) != null && !listUploadedData.get(11).isBlank()) {
					soLine.setManufacturerName(listUploadedData.get(11));
				}
				if (listUploadedData.size() > 12 && listUploadedData.get(12) != null && !listUploadedData.get(12).isBlank()) {
					soLine.setStorageSectionId(listUploadedData.get(12));
				}
				soLine.setPickListNo(listUploadedData.get(0));
				soLine.setSalesOrderNo(listUploadedData.get(1));
				soLines.add(soLine);
			}

			if (allRowsList.size() == i) {
				SalesOrderV2 salesOrder = new SalesOrderV2();
				salesOrder.setSalesOrderHeader(soHeader);
				salesOrder.setSalesOrderLine(soLines);
				salesOrderList.add(salesOrder);
			}
			i++;
		}

		return salesOrderList;
	}
}