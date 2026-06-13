package com.tekclover.wms.api.masters.transaction.service;

import com.tekclover.wms.api.masters.exception.BadRequestException;
import com.tekclover.wms.api.masters.transaction.model.dto.TImPartner;
import com.tekclover.wms.api.masters.transaction.repository.TImPartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TImPartnerService {

	@Autowired
	private TImPartnerRepository impartnerRepositoryT;

	public List<TImPartner> getImpartnerList(String companyCodeId, String plantId, String languageId, String warehouseId,
											 String partnerItemBarcode, String itemCode, String manufacturerName) {

		List<TImPartner> TImPartnerList =
				impartnerRepositoryT.findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndPartnerItemBarcodeAndItemCodeAndManufacturerNameAndDeletionIndicator(
						companyCodeId, plantId, languageId, warehouseId, partnerItemBarcode, itemCode, manufacturerName, 0L);
//		if(imPartnerList != null && !imPartnerList.isEmpty()) {
		log.info("Impartner List: " + TImPartnerList);
		return TImPartnerList;
//		}

//		throw new BadRequestException("ImPartner Not Found: " + partnerItemBarcode + ", " + itemCode + ", " + manufacturerName);
	}

	/**
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param partnerItemBarcode
	 * @return
	 */
	public List<TImPartner> getImpartnerBarcodeList(String companyCodeId, String plantId, String languageId, String warehouseId,
													String partnerItemBarcode) {

		List<TImPartner> TImPartnerList =
				impartnerRepositoryT.findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndPartnerItemBarcodeAndDeletionIndicator(
						companyCodeId, plantId, languageId, warehouseId, partnerItemBarcode, 0L);
//		if(imPartnerList != null && !imPartnerList.isEmpty()) {
		log.info("Impartner List: " + TImPartnerList);
		return TImPartnerList;
//		}
//		throw new BadRequestException("ImPartner Barcode Not Found: " + partnerItemBarcode );
	}

	/**
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param itemCode
	 * @param manufacturerName
	 * @return
	 */
	public List<TImPartner> getImpartnerItemCodeList(String companyCodeId, String plantId, String languageId,
													 String warehouseId, String itemCode, String manufacturerName) {

		List<TImPartner> TImPartnerList =
				impartnerRepositoryT.findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndItemCodeAndManufacturerNameAndDeletionIndicator(
						companyCodeId, plantId, languageId, warehouseId, itemCode, manufacturerName, 0L);
//		if(imPartnerList != null && !imPartnerList.isEmpty()) {
		log.info("Impartner List: " + TImPartnerList);
		return TImPartnerList;
//		}
//		throw new BadRequestException("ImPartner Not Found: " + itemCode + ", " + manufacturerName);
	}

	/**
	 * @param companyCodeId
	 * @param plantId
	 * @param languageId
	 * @param warehouseId
	 * @param itemCode
	 * @param manufacturerName
	 * @param barcodeId
	 * @param loginUserID
	 */
//	public List<ImPartner> updateImPartner (String companyCodeId, String plantId, String languageId, String warehouseId,
//											String itemCode, String manufacturerName, String barcodeId, String loginUserID) {
//
//		//Duplicate Barcode Validation
//		List<ImPartner> duplicateBarcodeId = impartnerRepository.findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndPartnerItemBarcodeAndDeletionIndicator(
//				companyCodeId, plantId, languageId, warehouseId, barcodeId, 0L);
//		log.info("Duplicate BarcodeId : " + duplicateBarcodeId);
//
//		if(duplicateBarcodeId != null && !duplicateBarcodeId.isEmpty()) {
//			throw new BadRequestException("BarcodeId already exist: " + barcodeId);
//		}
//
//		List<ImPartner> updatedBarcodeList = new ArrayList<>();
//		List<ImPartner> dbImpartnerList = impartnerRepository.findAllByCompanyCodeIdAndPlantIdAndWarehouseIdAndLanguageIdAndItemCodeAndManufacturerNameAndDeletionIndicator(
//				companyCodeId, plantId, warehouseId, languageId, itemCode, manufacturerName, 0L);
//		log.info("ImPartner: " + dbImpartnerList);
//		if (dbImpartnerList != null && !dbImpartnerList.isEmpty()) {
//			for(ImPartner imPartner :	dbImpartnerList) {
//				imPartner.setPartnerItemBarcode(barcodeId);
//				imPartner.setUpdatedBy(loginUserID);
//				imPartner.setUpdatedOn((new Date()));
//				impartnerRepository.save(imPartner);
//				updatedBarcodeList.add(imPartner);
//				log.info("ImPartner Updated: " + imPartner);
//			}
//		}
//		return updatedBarcodeList;
//	}
	public TImPartner updateImPartner(String companyCodeId, String plantId, String languageId, String warehouseId,
									  String itemCode, String manufacturerName, String barcodeId, String loginUserID) {
		try {
			//Duplicate Barcode Validation
			List<TImPartner> duplicateBarcodeCheck = impartnerRepositoryT.findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndPartnerItemBarcodeAndDeletionIndicator(
					companyCodeId, plantId, languageId, warehouseId, barcodeId, 0L);
			log.info("Duplicate BarcodeId : " + duplicateBarcodeCheck);
			if (duplicateBarcodeCheck != null && !duplicateBarcodeCheck.isEmpty()) {
				for (TImPartner dbTImPartner : duplicateBarcodeCheck) {
					String dbItemCode = dbTImPartner.getItemCode();
					String dbManufacturerName = dbTImPartner.getManufacturerName();
					String dbItmMfrName = dbItemCode + dbManufacturerName;
					String newItmMfrName = itemCode + manufacturerName;
					log.info("dbItmMfrName, newItmMfrName : " + dbItmMfrName + ", " + newItmMfrName);
					if (!dbItmMfrName.equalsIgnoreCase(newItmMfrName)) {
						throw new BadRequestException("BarcodeId already exist for different ItemCode MfrName: "
								+ barcodeId + ", " + dbItemCode + ", " + dbManufacturerName);
					}
				}
			}

			List<TImPartner> existingBarcodeId = impartnerRepositoryT.findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndItemCodeAndManufacturerNameAndPartnerItemBarcodeAndDeletionIndicator(
					companyCodeId, plantId, languageId, warehouseId, itemCode, manufacturerName, barcodeId, 0L);
			log.info("Existing BarcodeId : " + existingBarcodeId);

			if (existingBarcodeId != null && !existingBarcodeId.isEmpty()) {
				return existingBarcodeId.get(0);
			}

			TImPartner TImPartner = new TImPartner();
			TImPartner.setBusinessPartnerCode(manufacturerName);
			TImPartner.setCompanyCodeId(companyCodeId);
			TImPartner.setPlantId(plantId);
			TImPartner.setLanguageId(languageId);
			TImPartner.setWarehouseId(warehouseId);
			TImPartner.setItemCode(itemCode);
			TImPartner.setManufacturerName(manufacturerName);
			TImPartner.setManufacturerCode(manufacturerName);
			TImPartner.setBusinessPartnerType("1");
			TImPartner.setPartnerItemBarcode(barcodeId);
			TImPartner.setDeletionIndicator(0L);
			TImPartner.setCreatedBy(loginUserID);
			TImPartner.setCreatedOn(new Date());
			log.info("BarCode Created: " + TImPartner);
			return impartnerRepositoryT.save(TImPartner);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}