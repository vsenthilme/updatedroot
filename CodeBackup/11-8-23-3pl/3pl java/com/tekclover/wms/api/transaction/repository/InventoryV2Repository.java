package com.tekclover.wms.api.transaction.repository;

import com.tekclover.wms.api.transaction.model.inbound.inventory.v2.InventoryV2;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InventoryV2Repository extends PagingAndSortingRepository<InventoryV2,Long>, JpaSpecificationExecutor<InventoryV2> {


	List<InventoryV2> findByWarehouseIdAndItemCodeAndBinClassIdAndDeletionIndicator(
			String warehouseId, String itemCode, Long binClassId, Long deletionIndicator);

	List<InventoryV2> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndReferenceDocumentNoAndItemCodeAndDeletionIndicator(
			String languageId, String companyCodeId, String plantId,
			String warehouseId, String referenceDocumentNo, String itemCode, long l);

    InventoryV2 findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndPackBarcodesAndItemCodeAndStorageBinAndStockTypeIdAndSpecialStockIndicatorIdAndDeletionIndicator(
			String languageId, String companyCodeId, String plantId,
			String warehouseId, String packBarcodes, String itemCode,
			String storageBin, Long stockTypeId, Long specialStockIndicatorId, long l);

    Optional<InventoryV2> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndPackBarcodesAndItemCodeAndBinClassIdAndDeletionIndicator(
			String languageId, String companyCodeId, String plantId,
			String warehouseId, String packBarcodes, String itemCode, Long binClassId, long l);
}