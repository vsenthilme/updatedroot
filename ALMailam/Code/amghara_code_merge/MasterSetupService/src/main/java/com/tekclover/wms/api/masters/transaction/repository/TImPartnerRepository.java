package com.tekclover.wms.api.masters.transaction.repository;

import com.tekclover.wms.api.masters.transaction.model.dto.TImPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TImPartnerRepository extends JpaRepository<TImPartner,Long>, JpaSpecificationExecutor<TImPartner> {


	List<TImPartner> findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndPartnerItemBarcodeAndItemCodeAndManufacturerNameAndDeletionIndicator(
			String companyCodeId, String plantId, String languageId, String warehouseId,
			String partnerItemBarcode, String itemCode, String manufacturerName, Long deletionIndicator);

	List<TImPartner> findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndPartnerItemBarcodeAndDeletionIndicator(
			String companyCodeId, String plantId, String languageId, String warehouseId, String partnerItemBarcode, Long deletionIndicator);

	List<TImPartner> findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndItemCodeAndManufacturerNameAndDeletionIndicator(
			String companyCodeId, String plantId, String languageId, String warehouseId, String itemCode, String manufacturerName, Long deletionIndicator);

	List<TImPartner> findAllByCompanyCodeIdAndPlantIdAndWarehouseIdAndLanguageIdAndItemCodeAndManufacturerNameAndDeletionIndicator(
			String companyCodeId, String plantId, String warehouseId, String languageId, String itemCode, String manufacturerName, Long deletionIndicator);

    List<TImPartner> findAllByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndItemCodeAndManufacturerNameAndPartnerItemBarcodeAndDeletionIndicator(
			String companyCodeId, String plantId, String languageId, String warehouseId, String itemCode, String manufacturerName, String barcodeId, Long deletionIndicator);
}