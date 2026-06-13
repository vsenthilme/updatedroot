package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.gingerpaste.GingerPaste;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface GingerPasteRepository extends JpaRepository<GingerPaste, Long>,
        JpaSpecificationExecutor<GingerPaste>, StreamableJpaSpecificationRepository<GingerPaste> {

    Optional<GingerPaste> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId,
            String productionOrderNo, Long productionOrderLineNo, String receipeId,
            String operationNumber, String itemCode, Long deletionIndicator);

    List<GingerPaste> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, Long deletionIndicator);
}
