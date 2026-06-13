package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.dto.BomLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BomLineRepository extends JpaRepository<BomLine,Long>, JpaSpecificationExecutor<BomLine> {

	List<BomLine> findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndBomNumber(
			String languageId, String companyCode, String plantId, String warehouseId, Long bomNumber);

    Optional<BomLine> findByLanguageIdAndCompanyCodeAndPlantIdAndWarehouseIdAndBomNumberAndChildItemCodeAndDeletionIndicator(
			String languageId, String companyCode, String plantId, String warehouseId, Long bomNumber, String childItemCode, Long deletionIndicator);
}