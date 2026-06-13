package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.fgreceiving.FgReceiving;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FgReceivingRepository extends JpaRepository<FgReceiving, Long>,
        JpaSpecificationExecutor<FgReceiving>, StreamableJpaSpecificationRepository<FgReceiving> {

    Optional<FgReceiving> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo,
            Long productionOrderLineNo, String receipeId, String operationNumber, String itemCode, Long deletionIndicator
    );

    List<FgReceiving> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String productionOrderNo, Long deletionIndicator);

}
