package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.dto.BomHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface BomHeaderRepository extends JpaRepository<BomHeader,Long>, JpaSpecificationExecutor<BomHeader> {

	Optional<BomHeader> findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndParentItemCodeAndBomNumberAndDeletionIndicator(
			String languageId, String companyCode, String plantId, String warehouseId, String parentItemCode, String bomNumber, Long deletionIndicator);
}



